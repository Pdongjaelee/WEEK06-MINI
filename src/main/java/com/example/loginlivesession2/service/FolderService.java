package com.example.loginlivesession2.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.example.loginlivesession2.S3.CommonUtils;
import com.example.loginlivesession2.dto.*;
import com.example.loginlivesession2.entity.Folder;
import com.example.loginlivesession2.entity.Member;
import com.example.loginlivesession2.entity.Photo;
import com.example.loginlivesession2.exception.ErrorCode;
import com.example.loginlivesession2.exception.RequestException;
import com.example.loginlivesession2.repository.FolderRepository;
import com.example.loginlivesession2.repository.MemberRepository;
import com.example.loginlivesession2.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final AmazonS3Client amazonS3Client;

    private final FolderRepository folderRepository;

    private final PhotoRepository photoRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Transactional
    public String addPhotos(List<MultipartFile> multipartFile, Long folderId, Member member) throws IOException {
        Folder folder = folderObject(folderId);

        String imgurl = "";

        for (MultipartFile file : multipartFile) {
            if (!file.isEmpty()) {
                String fileName = CommonUtils.buildFileName(file.getOriginalFilename());
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(file.getContentType());

                byte[] bytes = IOUtils.toByteArray(file.getInputStream());
                objectMetadata.setContentLength(bytes.length);
                ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

                amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, byteArrayIs, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                imgurl = amazonS3Client.getUrl(bucketName, fileName).toString();

                Photo photo = new Photo(folder, imgurl);

                photoRepository.save(photo);
            }
        }
            return "사진이 추가되었습니다!";
    }

    @Transactional(readOnly = true)
    public FolderPageResDto showFolderPage(Long folderId, Member member) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(
                ()-> new RequestException(ErrorCode.FOLDER_ID_NOT_FOUND_404));
        authorityCheck(folder, member);

        List<FolderResDto> folderResDtoList = new ArrayList<>();

        List<Photo> photos = photoRepository.findAllByFolderId(folderId);
        for (Photo photo : photos) {
            FolderResDto folderResDto = new FolderResDto(photo);
            folderResDtoList.add(folderResDto);
            }

        List<TagResDto> tagResDtoList = new ArrayList<>();

        if (folder.getTags().length() != 0) {
            String[] tagList = folder.getTags().substring(1).split("#");
            for (String s : tagList) {
                TagResDto tagResDto = new TagResDto("#" + s);
                tagResDtoList.add(tagResDto);
            }
        }
        return new FolderPageResDto(folderResDtoList,tagResDtoList);
    }

//    public static void main(String[] args) {
//        String filepath = "/the/file/path/image.jpg";
//        File f = new File(filepath);
//        String mimetype= new MimetypesFileTypeMap().getContentType(f);
//        String type = mimetype.split("/")[0];
//        if(type.equals("image"))
//            System.out.println("It's an image");
//        else
//            System.out.println("It's NOT an image");
//    }
//}
    @Transactional
    public String updateTag(Long folderId, TagReqDto tagReqDto, Member member){
        Folder folder = folderObject(folderId);
        authorityCheck(folder, member);
        folder.updateTag(listToString(tagReqDto.getTagList()));
        return "수정 완료";
    }

     private void folderIdCheck(Long id) {
        folderRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.FOLDER_ID_NOT_FOUND_404)
        );
    }

    private void authorityCheck(Folder folder, Member member) {
        if(!folder.getMember().getUserId().equals(member.getUserId())){
            throw new RequestException(ErrorCode.UNAUTHORIZED_401);
        }
    }


    private Folder folderObject(Long id) {
        return folderRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.FOLDER_ID_NOT_FOUND_404)
        );

    }
    private String listToString(List<String> tagList) {
        StringBuilder tag = new StringBuilder();
        for (String s : tagList) tag.append(s);
        return tag.toString();
    }



}



