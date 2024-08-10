package com.example.grocery_store_sales_online.service.image.impl;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.File.Image;
import com.example.grocery_store_sales_online.repository.image.IImageRepository;
import com.example.grocery_store_sales_online.repository.image.impl.ImageRepository;
import com.example.grocery_store_sales_online.service.base.impl.BaseService;
import com.example.grocery_store_sales_online.service.image.IImageService;
import com.example.grocery_store_sales_online.service.s3.S3Service;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.mysema.commons.lang.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.grocery_store_sales_online.utils.CommonConstants.SLASH;

@Service
@RequiredArgsConstructor
@Slf4j
public class imagesService extends BaseService implements IImageService {
    private final ImageRepository imageRepository;
    private final S3Service s3Service;
    public static final float DEFAULT_M_SIZE = (float)345/465;
    public static final float DEFAULT_S_SIZE = (float)65/90;
    private static final int MEDIUM_WIDTH = 600;
    private static final int SMALL_WIDTH = 300;
    @Value("${aws.s3.buckets.customer}")
    private String s3BucketsCustomer;
    @Value("${aws.s3.buckets.domain}")
    private String s3BucketsDomain;
    @Value("${filestore.folder.image}")
    private String folderStoreImage;
    @Override
    public Image saveModel(Image model) {
        log.info("imagesService:saveModel(image) execution started.");
        try {
            return imageRepository.saveModel(model);
        }catch (Exception ex){
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
    public Image hanldeToImage(MultipartFile file,String directory) {
            boolean isStoredFailed = false;
            Image image = new Image();
            if (file != null && file.getContentType() != null && !file.isEmpty() && file.getOriginalFilename()!=null) {
                try {
                    image.setName(file.getOriginalFilename());
                    image.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(CommonConstants.DOT) + 1).toLowerCase());
                    image.setDescription(file.getName());
                    this.saveMediumAndSmall(file, directory);
                    image.setImageUrl(folderStoreImage.concat(directory+file.getOriginalFilename()));
                    image.setMedium(folderStoreImage.concat(directory+"m"+CommonConstants.UNDER_SCOPE+file.getOriginalFilename()));
                    image.setSmall(folderStoreImage.concat(directory+"s"+CommonConstants.UNDER_SCOPE+file.getOriginalFilename()));
                } catch (Exception e) {
                    isStoredFailed = true;
                }
            }
            if (!isStoredFailed) {
                return image;
            }
            throw new ServiceBusinessExceptional(EResponseStatus.CONFIG_IMAGE_FAIL.getLabel(), EResponseStatus.CONFIG_IMAGE_FAIL.getCode());
    }
    private void saveMediumAndSmall(MultipartFile image, String keyFileStore) {
        log.info("ResizeImage:ResizeImage execution started.");
        try {
            if (!image.isEmpty()) {
                BufferedImage originalImage = ImageIO.read(image.getInputStream());
                List<Pair<Integer, Integer>> listSize = getHeightSmallAndMedium2();
                String extension = image.getName().substring(image.getName().lastIndexOf(CommonConstants.DOT) + 1);
                s3Service.putObject(s3BucketsCustomer,folderStoreImage+keyFileStore+image.getOriginalFilename(),image);
                if (!listSize.isEmpty()) {
                    String outFileStr = keyFileStore + "m_" + image.getOriginalFilename();
                    ByteArrayOutputStream outFile = new ByteArrayOutputStream();
                    int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                    BufferedImage resizeImagePng = resizeImage(originalImage, type, listSize.get(0).getFirst(), listSize.get(0).getSecond());
                    ImageIO.write(resizeImagePng, extension, outFile);
                    s3Service.putObject(s3BucketsCustomer,folderStoreImage+outFileStr,image);
                }
                if (listSize.size() == 2) {
                    String outFileStr = keyFileStore + "s_" + image.getOriginalFilename();
                    ByteArrayOutputStream outFile = new ByteArrayOutputStream();
                    int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                    BufferedImage resizeImagePng = resizeImage(originalImage, type, listSize.get(0).getFirst(), listSize.get(0).getSecond());
                    ImageIO.write(resizeImagePng, extension, outFile);
                    s3Service.putObject(s3BucketsCustomer,folderStoreImage+outFileStr,image);
                }
            }
        } catch (Exception ex) {
            log.error("Exception occurred while persisting ResizeImage:ResizeImage to database and aws , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.AWS_UPLOAD_IMAGE_FAIL.getLabel(), EResponseStatus.AWS_LOAD_IMAGE_FAIL.getCode());
        }
    }
    private String folderUrl() {
        return folderStoreImage + SLASH
                + getClass().getSimpleName().toLowerCase() + SLASH;
    }
    private static List<Pair<Integer, Integer>> getHeightSmallAndMedium2() {
        int heightMedium = 0;
        int heightSmall = 0;
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        int widthMediumConf = MEDIUM_WIDTH;
        int widthSmallConf = SMALL_WIDTH;
        if (widthMediumConf != 0) {
            heightMedium = (int) (widthMediumConf*DEFAULT_M_SIZE);
            Pair<Integer, Integer> pairMedium = new Pair<>(widthMediumConf, heightMedium);
            list.add(pairMedium);
        }
        if (widthSmallConf != 0) {
            heightSmall = (int) (widthSmallConf*DEFAULT_S_SIZE);
            Pair<Integer, Integer> pairSmall = new Pair<>(widthSmallConf,heightSmall);
            list.add(pairSmall);
        }
        return list;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage,
                                             int type, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        BufferedImage thumbImage = new BufferedImage(width, height,
                type);

        if((double)width/height >= (double)originalImage.getWidth()/originalImage.getHeight()){
            // Fit to width
            resizedImage = Scalr.resize(originalImage, Scalr.Mode.FIT_TO_WIDTH,
                    width, height, Scalr.OP_ANTIALIAS);
            Graphics2D tGraphics2D = thumbImage.createGraphics(); //create a graphics object to paint to
            tGraphics2D.drawImage(resizedImage, 0, 0, width, height, 0,
                    (resizedImage.getHeight() - height)/2 , width,
                    (resizedImage.getHeight() - height)/2 + height, null); //draw the image scaled */
            tGraphics2D.dispose();
            // Fit to height
        }else{
            resizedImage = Scalr.resize(originalImage, Scalr.Mode.FIT_TO_HEIGHT,
                    width, height, Scalr.OP_ANTIALIAS);
            Graphics2D tGraphics2D = thumbImage.createGraphics(); //create a graphics object to paint to
            tGraphics2D.drawImage(resizedImage, 0, 0, width, height, (resizedImage.getWidth() - width)/2, 0,
                    (resizedImage.getWidth() - width)/2 + width, height, null); //draw the image scaled */
            tGraphics2D.dispose();
        }
        return thumbImage;
    }


}
