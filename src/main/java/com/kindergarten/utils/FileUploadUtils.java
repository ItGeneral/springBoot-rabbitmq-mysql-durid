package com.kindergarten.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * @Date Created on 2017/8/4.
 * @Author SongJiuHua.
 * @description 文件上传和下载
 */
public class FileUploadUtils {

    private static Properties properties = new Properties();
    static {
        try {
            InputStream in = new BufferedInputStream(new FileInputStream("file.properties"));
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 上传文件
     * @param request
     * @param response
     */
    public static void upLoadFile(HttpServletRequest request, HttpServletResponse response){
        //文件保存目录
        String fileSavePath = properties.getProperty("file.save.path");
        //文件保存临时目录
        String fileTmpPath = properties.getProperty("file.tem.path");
        //创建临时目录
        File tmpFile = new File(fileTmpPath);
        if (!tmpFile.exists()){
            tmpFile.mkdir();
        }
        try{
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
            factory.setSizeThreshold(1024*100);//设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
            //设置上传时生成的临时文件的保存目录
            factory.setRepository(tmpFile);
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //监听文件上传进度
            upload.setProgressListener(new ProgressListener(){
                public void update(long pBytesRead, long pContentLength, int arg2) {
                    System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
                }
            });
            //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            //3、判断提交上来的数据是否是上传表单的数据
            if(!ServletFileUpload.isMultipartContent(request)){
                return;
            }
            //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
            upload.setFileSizeMax(1024*1024);
            //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
            upload.setSizeMax(1024*1024*10);
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> fileItemList = upload.parseRequest(request);
            for (FileItem item : fileItemList){
                //如果fileitem中封装的是普通输入项的数据
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                }else{//如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();
                    if(filename==null || filename.trim().equals("")){
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\")+1);
                    //得到上传文件的扩展名
                    String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
                    //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                    System.out.println("上传的文件的扩展名是："+fileExtName);
                    //获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //得到文件保存的名称
                    String saveFilename = makeFileName(filename);
                    //得到文件的保存目录
                    String realSavePath = makePath(saveFilename, fileSavePath);
                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(realSavePath + "\\" + saveFilename);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while((len=in.read(buffer))>0){
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
                    //item.delete();
                }
            }
        }catch (FileUploadBase.FileSizeLimitExceededException e) {
            e.printStackTrace();
            request.setAttribute("message", "单个文件超出最大值！！！");
            return;
        }catch (FileUploadBase.SizeLimitExceededException e) {
             e.printStackTrace();
             request.setAttribute("message", "上传文件的总的大小超出限制的最大值！！！");
             return;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
     * @param filename 文件的原始名称
     * @return uuid+"_"+文件的原始名称
     */
    private static String makeFileName(String filename){  //2.jpg
        //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        return UUID.randomUUID().toString() + "_" + filename;
    }

    /**
     * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
     * @param filename 文件名，要根据文件名生成存储目录
     * @param savePath 文件存储路径
     * @return 新的存储目录
     */
    private static String makePath(String filename,String savePath){
         //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
         int hashcode = filename.hashCode();
         int dir1 = hashcode&0xf;  //0--15
         int dir2 = (hashcode&0xf0)>>4;  //0-15
         //构造新的保存目录
         String dir = savePath + "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5
         //File既可以代表文件也可以代表目录
         File file = new File(dir);
         //如果目录不存在
         if(!file.exists()){
             //创建目录
             file.mkdirs();
         }
         return dir;
    }

    /**
     * 文件查询
     * @param request
     * @param response
     */
    public static void queryFiles(HttpServletRequest request, HttpServletResponse response){
        //获取上传文件的目录
         String uploadFilePath = properties.getProperty("file.save.path");
         //存储要下载的文件名
         Map<String,String> fileNameMap = new HashMap<String,String>();
         //递归遍历filepath目录下的所有文件和目录，将文件的文件名存储到map集合中
         listfile(new File(uploadFilePath),fileNameMap);//File既可以代表一个文件也可以代表一个目录
         //将Map集合发送到listfile.jsp页面进行显示
         request.setAttribute("fileNameMap", fileNameMap);
    }

    public static void listfile(File file,Map<String,String> map){
        //如果file代表的不是一个文件，而是一个目录
        if(!file.isFile()){
            //列出该目录下的所有文件和目录
            File files[] = file.listFiles();
            //遍历files[]数组
            for(File f : files){
                //递归
                listfile(f,map);
            }
        }else{
            /**
            * 处理文件名，上传后的文件是以uuid_文件名的形式去重新命名的，去除文件名的uuid_部分
            file.getName().indexOf("_")检索字符串中第一次出现"_"字符的位置，如果文件名类似于：9349249849-88343-8344_阿_凡_达.avi
            那么file.getName().substring(file.getName().indexOf("_")+1)处理之后就可以得到阿_凡_达.avi部分
            */
            String realName = file.getName().substring(file.getName().indexOf("_")+1);
            //file.getName()得到的是文件的原始名称，这个名称是唯一的，因此可以作为key，realName是处理过后的名称，有可能会重复
            map.put(file.getName(), realName);
        }
    }


    /**
     * 文件下载
     * @param request
     * @param response
     * @throws IOException
     */
    public static void downloadFiles(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //得到要下载的文件名
        String fileName = request.getParameter("filename");  //23239283-92489-阿凡达.avi
        fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
        //上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
        String fileSaveRootPath = properties.getProperty("file.save.path");
        //通过文件名找出文件的所在目录
        String path = makePath(fileName,fileSaveRootPath);
        //得到要下载的文件
        File file = new File(path + "\\" + fileName);
        //如果文件不存在
        if(!file.exists()){
             request.setAttribute("message", "您要下载的资源已被删除！！");
             return;
        }
        //处理文件名
        String realname = fileName.substring(fileName.indexOf("_")+1);
        //设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
        //读取要下载的文件，保存到文件输入流
        FileInputStream in = new FileInputStream(path + "\\" + fileName);
        //创建输出流
        OutputStream out = response.getOutputStream();
        //创建缓冲区
        byte buffer[] = new byte[1024];
        int len = 0;
        //循环将输入流中的内容读取到缓冲区当中
        while((len=in.read(buffer))>0){
            //输出缓冲区的内容到浏览器，实现文件下载
             out.write(buffer, 0, len);
         }
        //关闭文件输入流
        in.close();
        //关闭输出流
        out.close();
    }


}
