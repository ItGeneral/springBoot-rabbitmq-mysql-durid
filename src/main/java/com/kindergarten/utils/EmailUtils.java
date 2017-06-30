package com.kindergarten.utils;

import com.alibaba.fastjson.util.IOUtils;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 邮件工具类
 * Created by kevensong on 15/12/10.
 *
 * @author huang.peijie refactor
 * @since 2016/01/03
 */
public class EmailUtils {

    /*public static void main(String[] args) throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("799178171@qq.com");
//        List<String> attachList = new ArrayList<>();
//        attachList.add("");
        EmailUtils.from("发送邮箱地址", "邮箱密码")
//                .to("")
                //.to(list)
                .cc(list)
                .subject("邮件测试113")
                .body("Dear 113,<br>ahahahahahahahahaha但是颠三倒四a")
                .attachment("G:\\Project\\deployWar\\src\\main\\java\\com\\deploy\\comtroller\\TestController.java")
                //.attachment("123.xlsx",new FileInputStream(new File("/Users/kevensong/project/td-utils/src/test/java/com/dooioo/td/utils/TestIs.java")))
//                .attach(attachList)
                .send();
    }*/

    // 邮箱服务器信息
    private Properties props;
    // 发送人
    private String from;
    private String fromPwd;
    // 收件人
    private Set<Address> tos = new HashSet<>();
    private Set<Address> ccs = new HashSet<>();
    private Set<Address> bccs = new HashSet<>();
    // 主题
    private String subject;
    // 正文
    private String body;
    // 附件
    private Set<String> attachmentPathList = new HashSet<>();
    private Map<String, InputStream> attachmentStreamMap = new HashMap<>();
    // 发送日期
    private Date sendDate;

    static {
        // 初始化邮件session
        Properties p = new Properties();
        p.put("mail.smtp.host", "smtp.163.com");
        p.put("mail.transport.protocol", "smtp");
        p.put("mail.smtp.port", "25");
        p.put("mail.smtp.auth",  "true");
        Session.getDefaultInstance(p);
    }

    public static EmailUtils from(String from, String fromPwd) throws AddressException {
        return new EmailUtils(from, fromPwd);
    }

    public EmailUtils props(Properties props) {
        this.props = props;
        return this;
    }

    public EmailUtils(String from, String fromPwd) throws AddressException {
        this.from = from;
        this.fromPwd = fromPwd;
    }

    public EmailUtils to(String... tos) throws AddressException {
        for (String to : tos) {
            if(StringUtils.isEmpty(to)) continue;
            this.tos.add(new InternetAddress(to));
        }
        return this;
    }

    public EmailUtils to(Collection<String> toList) throws AddressException {
        for (String to : toList) {
            if(StringUtils.isEmpty(to)) continue;
            this.tos.add(new InternetAddress(to));
        }
        return this;
    }

    public EmailUtils cc(String... ccs) throws AddressException {
        for (String cc : ccs) {
            if(StringUtils.isEmpty(cc)) continue;
            this.ccs.add(new InternetAddress(cc));
        }
        return this;
    }

    public EmailUtils cc(Collection<String> ccList) throws AddressException {
        for (String cc : ccList) {
            if(StringUtils.isEmpty(cc)) continue;
            this.ccs.add(new InternetAddress(cc));
        }
        return this;
    }

    public EmailUtils bcc(String... bccs) throws AddressException {
        for (String bcc : bccs) {
            if(StringUtils.isEmpty(bcc)) continue;
            this.bccs.add(new InternetAddress(bcc));
        }
        return this;
    }

    public EmailUtils bcc(Collection<String> bccList) throws AddressException {
        for (String bcc : bccList) {
            if(StringUtils.isEmpty(bcc)) continue;
            this.bccs.add(new InternetAddress(bcc));
        }
        return this;
    }

    public EmailUtils subject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailUtils body(String body) {
        this.body = body;
        return this;
    }

    public EmailUtils attachment(String... attachments) {
        for (String attachment : attachments) {
            if (!new File(attachment).exists()) continue;
            this.attachmentPathList.add(attachment);
        }
        return this;
    }

    public EmailUtils attachment(Collection<String> attachmentList) {
        for (String attachment : attachmentList) {
            if (!new File(attachment).exists()) continue;
            this.attachmentPathList.add(attachment);
        }
        return this;
    }

    public EmailUtils attachment(String name, InputStream in) {
        attachmentStreamMap.put(name, in);
        return this;
    }

    public EmailUtils sendDate(Date sendDate) {
        this.sendDate = sendDate;
        return this;
    }

    /**
     * 获取邮件发送的session
     * 默认session已经初始化,所以只用传一个null,里面不会用这个Properties
     *
     * @return
     */
    private Session getSession() {
        return props == null ? Session.getDefaultInstance(null) : Session.getInstance(props);
    }

    /**
     * 构建邮件消息对象,包含发件人,收件人,主题,发送时间
     *
     * @param session
     * @return
     * @throws MessagingException
     */
    private MimeMessage buildMimeMessage(Session session) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(from));
        fillRecipients(mimeMessage);
        mimeMessage.setSubject(subject);
        //设置邮件发送时间，如果传入的时间小于当前的时间，则发送时间为当前时间，否则为传入的时间
        mimeMessage.setSentDate((sendDate == null || new Date().getTime() > sendDate.getTime())
                ? new Date() : sendDate);
        return mimeMessage;
    }

    /**
     * 填充收件人,抄送,暗送
     *
     * @param mimeMessage
     * @throws MessagingException
     */
    private void fillRecipients(MimeMessage mimeMessage) throws MessagingException {
        mimeMessage.setRecipients(Message.RecipientType.TO, tos.toArray(new InternetAddress[]{}));
        if (!StringUtils.isEmpty(ccs)) mimeMessage.setRecipients(Message.RecipientType.CC, ccs.toArray(new InternetAddress[]{}));
        if (!StringUtils.isEmpty(bccs))
            mimeMessage.setRecipients(Message.RecipientType.BCC, bccs.toArray(new InternetAddress[]{}));
    }

    /**
     * 添加邮件正文
     *
     * @param multipart
     * @throws MessagingException
     */
    private void addBody(Multipart multipart) throws MessagingException {
        if (!StringUtils.isEmpty(body)) {
            BodyPart contentBodyPart = new MimeBodyPart();
            contentBodyPart.setContent(body, "text/html; charset=utf-8");
            multipart.addBodyPart(contentBodyPart);
        }
    }

    /**
     * 添加邮件附件
     *
     * @param multipart
     * @throws MessagingException
     * @throws IOException
     */
    private void addAttachment(Multipart multipart) throws MessagingException, IOException {
        for (String attachmentPath : attachmentPathList) {
            if (StringUtils.isEmpty(attachmentPath)) continue;
            BodyPart bodyPart = new MimeBodyPart();
            FileDataSource fileDataSource = new FileDataSource(attachmentPath);
            bodyPart.setDataHandler(new DataHandler(fileDataSource));
            bodyPart.setFileName(MimeUtility.encodeWord(fileDataSource.getName(), "utf-8", null));
            multipart.addBodyPart(bodyPart);
        }
        for (String fileName : attachmentStreamMap.keySet()) {
            if (StringUtils.isEmpty(fileName)) continue;
            InputStream content = attachmentStreamMap.get(fileName);
            //根据文件名获取mimeType
            String mimeType = new MimetypesFileTypeMap().getContentType(fileName);
            BodyPart bodyPart = new MimeBodyPart();
            ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(content, mimeType);
            bodyPart.setDataHandler(new DataHandler(byteArrayDataSource));
            bodyPart.setFileName(MimeUtility.encodeWord(fileName, "utf-8", null));
            multipart.addBodyPart(bodyPart);
            IOUtils.close(content);
        }
    }

    /**
     * 删除无效地址
     *
     * @param invalidAddresses
     */
    private void removeInValid(Address[] invalidAddresses) {
        for (Address address : invalidAddresses) {
            tos.remove(address);
            ccs.remove(address);
            bccs.remove(address);
        }
    }

    /**
     * 开始发送邮件
     *
     * @throws MessagingException
     * @throws IOException
     */
    public void send() throws MessagingException, IOException {
        Session session = getSession();
        MimeMessage mimeMessage = buildMimeMessage(session);
        Multipart multipart = new MimeMultipart();
        addBody(multipart);
        addAttachment(multipart);
        mimeMessage.setContent(multipart);

        Transport transport = session.getTransport();
        transport.connect(from, fromPwd);
        try {
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        } catch (SendFailedException e) {
            removeInValid(e.getInvalidAddresses());
            fillRecipients(mimeMessage);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        }
        transport.close();
    }

}
