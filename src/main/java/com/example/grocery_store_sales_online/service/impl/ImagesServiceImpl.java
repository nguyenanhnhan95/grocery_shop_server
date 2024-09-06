package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.File.Image;
import com.example.grocery_store_sales_online.repository.image.impl.ImageRepository;
import com.example.grocery_store_sales_online.service.IImageService;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.CommonUtils;
import com.example.grocery_store_sales_online.utils.ProcessImage;
import com.mysema.commons.lang.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static com.example.grocery_store_sales_online.utils.CommonConstants.SLASH;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImagesServiceImpl extends BaseServiceImpl implements IImageService {
    private final ImageRepository imageRepository;
    private final S3ServiceImpl s3Service;

    @Value("${aws.s3.buckets.customer}")
    private String s3BucketsCustomer;
    @Value("${aws.s3.buckets.domain}")
    private String s3BucketsDomain;
    @Value("${filestore.folder.image}")
    private String folderStoreImage;
    @Value("${filestore.folder.image.avatar}")
    private String folderStoreAvatar;
    @Override
    public Image saveModel(Image model) {
        log.info("imagesService:saveModel(image) execution started.");
        try {
            return imageRepository.saveModel(model);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting imagesService:saveModel database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public String saveImage(MultipartFile image) {
        boolean isStoredFailed = false;
//        if (image != null && image.getContentType() != null && !image.isEmpty()) {
//            final File baseDir = new File(folderStore.concat(image.getName()));
//            try {
//                Files.copy(baseDir, image.getInputStream());
//                saveImageToServer(image, true);
//            } catch (Exception e) {
//                isStoredFailed = true;
//            }
//        }
//        if (!isStoredFailed) {
//            saveModel(image);
//        }
        return null;
    }

    @Override
    public String handleImageToServerByUrl(String imageUrl, String directory) {
        log.info("imagesService:handleImageToServerByUrl execution started.");
        try {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                URL url = new URL(imageUrl);
                URLConnection connection = url.openConnection();
                BufferedImage image = ImageIO.read(url);
                String extension = CommonUtils.getFileExtensionFromTypeContent(connection.getContentType());
                byte[] buffer = writeImageToByteArray(image, extension);
                String pathImage = directory + CommonConstants.DOT + extension;


                s3Service.putImage(s3BucketsCustomer, pathImage, buffer);
                return pathImage;
            }
            throw new ServiceBusinessExceptional(EResponseStatus.SEND_TO_IMAGE_SERVER_FAIL.getLabel(), EResponseStatus.SEND_TO_IMAGE_SERVER_FAIL.getCode());
        } catch (Exception ex) {
            log.error("Exception occurred while  imagesService:handleImageToServerByUrl to image to aws , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SEND_TO_IMAGE_SERVER_FAIL.getLabel(), EResponseStatus.SEND_TO_IMAGE_SERVER_FAIL.getCode());
        }
    }

    @Override
    public String handleImageAvatarToAws(MultipartFile image, String directory ,String olderKey) {
        try {
            BufferedImage originalImage = ImageIO.read(image.getInputStream());
            String extension = CommonUtils.getFileExtension(image.getOriginalFilename());
            List<Pair<Integer, Integer>> listSize = ProcessImage.getHeightSmallAndMedium2();
            String absolutePath = folderStoreAvatar+directory+CommonConstants.DOT+extension;
            if(s3Service.checkKeyFileExisting(image.getOriginalFilename())){
                return olderKey;
            }
            if (s3Service.checkKeyFileExisting(absolutePath) ) {
                return absolutePath;
            }
            if(olderKey!=null && olderKey.contains(folderStoreAvatar)){
                s3Service.deleteObject(s3BucketsCustomer,olderKey);
            }
            if (!listSize.isEmpty()) {
                if (listSize.size() == 2) {
                    int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                    BufferedImage resizeImagePng = ProcessImage.resizeImage(originalImage, type, listSize.get(1).getFirst(), listSize.get(1).getSecond());
                    byte[] bufferSmall = writeImageToByteArray(resizeImagePng, extension);
                    s3Service.putImage(s3BucketsCustomer, absolutePath , bufferSmall);
                    return absolutePath;
                }
            }
            throw new ServiceBusinessExceptional(EResponseStatus.AWS_UPLOAD_IMAGE_FAIL.getLabel(), EResponseStatus.AWS_UPLOAD_IMAGE_FAIL.getCode());
        } catch (Exception ex) {
            log.error("Exception occurred while persisting imagesService:handleToImage config file , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.AWS_UPLOAD_IMAGE_FAIL.getLabel(), EResponseStatus.AWS_UPLOAD_IMAGE_FAIL.getCode());
        }
    }

    @Override
    public Image handleToImage(MultipartFile file, String directory) {
        boolean isStoredFailed = false;
        log.info("imagesService:handleToImage execution started.");
        Image image = new Image();
        if (file != null && !file.isEmpty() && file.getContentType() != null && file.getOriginalFilename() != null && ProcessImage.checkExtensionImage(file.getOriginalFilename())) {

            try {
                image.setName(file.getOriginalFilename());
                image.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(CommonConstants.DOT) + 1).toLowerCase());
                image.setDescription(file.getName());
                this.saveMediumAndSmall(file, directory);
                image.setImageUrl(folderStoreImage.concat(directory + file.getOriginalFilename()));
                image.setMedium(folderStoreImage.concat(directory + "m" + CommonConstants.UNDER_SCOPE + file.getOriginalFilename()));
                image.setSmall(folderStoreImage.concat(directory + "s" + CommonConstants.UNDER_SCOPE + file.getOriginalFilename()));
            } catch (Exception ex) {
                log.error("Exception occurred while persisting imagesService:handleToImage config file , Exception message {}", ex.getMessage());
                isStoredFailed = true;
            }
        }
        if (!isStoredFailed) {
            return image;
        }
        log.error("imagesService:hanldeToImage file not exist.");
        throw new ServiceBusinessExceptional(EResponseStatus.CONFIG_IMAGE_FAIL.getLabel(), EResponseStatus.CONFIG_IMAGE_FAIL.getCode());
    }

    private void saveMediumAndSmall(MultipartFile image, String keyFileStore) {
        log.info("imagesService:ResizeImage execution started.");
        try {
            BufferedImage originalImage = ImageIO.read(image.getInputStream());
            List<Pair<Integer, Integer>> listSize = ProcessImage.getHeightSmallAndMedium2();
            String extension = CommonUtils.getFileExtension(image.getOriginalFilename());
            String absolutePath = folderStoreImage + keyFileStore + image.getOriginalFilename();
            if (s3Service.checkKeyFileExisting(absolutePath)) {
                return;
            }
            byte[] buffer = writeImageToByteArray(originalImage, extension);
            s3Service.putImage(s3BucketsCustomer, absolutePath, buffer);
            if (!listSize.isEmpty()) {
                String outFileStr = keyFileStore + "m_" + image.getOriginalFilename();
                int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                BufferedImage resizeImagePng = ProcessImage.resizeImage(originalImage, type, listSize.get(0).getFirst(), listSize.get(0).getSecond());
                byte[] bufferMedium = writeImageToByteArray(resizeImagePng, extension);
                s3Service.putImage(s3BucketsCustomer, folderStoreImage + outFileStr, bufferMedium);
            }
            if (listSize.size() == 2) {
                String outFileStr = keyFileStore + "s_" + image.getOriginalFilename();
                int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                BufferedImage resizeImagePng = ProcessImage.resizeImage(originalImage, type, listSize.get(1).getFirst(), listSize.get(1).getSecond());
                byte[] bufferSmall = writeImageToByteArray(resizeImagePng, extension);
                s3Service.putImage(s3BucketsCustomer, folderStoreImage + outFileStr, bufferSmall);
            }
        } catch (Exception ex) {
            log.error("Exception occurred while persisting imagesService:ResizeImage to database and aws , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.AWS_UPLOAD_IMAGE_FAIL.getLabel(), EResponseStatus.AWS_LOAD_IMAGE_FAIL.getCode());
        }
    }

    private byte[] writeImageToByteArray(BufferedImage image, String extension) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(image, extension, os);
            return os.toByteArray();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting imagesService:writeImageToByteArray to database and aws , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.AWS_UPLOAD_IMAGE_FAIL.getLabel(), EResponseStatus.AWS_LOAD_IMAGE_FAIL.getCode());
        }
    }

    private String folderUrl() {
        return folderStoreImage + SLASH
                + getClass().getSimpleName().toLowerCase() + SLASH;
    }


}
