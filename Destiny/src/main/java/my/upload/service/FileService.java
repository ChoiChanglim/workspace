package my.upload.service;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;






import my.random.api.exception.CustomException;
import my.random.api.util.StringUtil;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Service
public class FileService {
	@Value("#{propinfo['STORED_IMG_PATH']}")
    private String STORED_IMG_PATH;
	
	@Value("#{propinfo['STATIC_IMG_PATH']}")
    private String STATIC_IMG_PATH;
	

    public List<HashMap<String, Object>> fileSave(MultipartHttpServletRequest multipartHttpServletRequest){
        if(multipartHttpServletRequest.getFileNames().hasNext()==false){
            throw CustomException.EMPTY_ATTACH_FILE;
        }

        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;

        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        File file = new File(STORED_IMG_PATH);
        if(file.exists() == false){
            file.mkdirs();
        }
        while(iterator.hasNext()){
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            if(multipartFile.isEmpty() == false){
                originalFileName = multipartFile.getOriginalFilename();
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                storedFileName = StringUtil.getRandomString() + originalFileExtension;
                file = new File(STORED_IMG_PATH + storedFileName);
                try {
                    multipartFile.transferTo(file);
                } catch (IllegalStateException | IOException e) {
                    throw CustomException.UploadFileSaveException;
                }

                HashMap<String, Object> result = new HashMap<String, Object>();
                result.put("originalFileName", originalFileName);
                result.put("storedFileName", storedFileName);
                result.put("fileSize", multipartFile.getSize());
                result.put("mimeType", getMimeTypeByFile(file));
                result.put("fullPath", STATIC_IMG_PATH+storedFileName);
                list.add(result);
            }
            
        }
        return list;
    }



    public boolean isImageFiles(HttpServletRequest req) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)req;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        MultipartFile multipartFile = null;
        boolean isImageFile = true;
        while(iterator.hasNext()){
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            if(multipartFile.isEmpty() == false){
                try {
                    String mimeType = getMimeTypeByInputStream(multipartFile.getInputStream());
                    if(mimeType.contains("image") == false){
                        isImageFile = false;
                        throw CustomException.INVALID_IMAGE_FILE;
                    }
                } catch (IOException e) {

                }

            }
        }
        return isImageFile;
    }

    public String getMimeTypeByFile(File file){
        String mimeType = null;
        try {
            Tika tika = new Tika();
            mimeType = tika.detect(file);
        }catch(Exception e){
            throw CustomException.MimeTypeDetectException;
        }
        return mimeType;
    }

    public String getMimeTypeByInputStream(InputStream inputStream){
        String mimeType = null;
        try {
            Tika tika = new Tika();
            mimeType = tika.detect(inputStream);
        }catch(Exception e){
            throw CustomException.MimeTypeDetectException;
        }
        return mimeType;
    }

    public String getMimeTypeByByte(byte[] bt){
        String mimeType = null;
        try {
            Tika tika = new Tika();
            mimeType = tika.detect(bt);
        }catch(Exception e){
            throw CustomException.MimeTypeDetectException;
        }
        return mimeType;
    }
}