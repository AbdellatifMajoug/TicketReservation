
package org.sid.springmvc.services;

import java.io.IOException;
import org.sid.springmvc.dao.ImageRepository;
import org.sid.springmvc.entities.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageStorageService {
	@Autowired
	private ImageRepository imageRepository;
	
	public Image saveFile(MultipartFile file) {
		String imgName = file.getOriginalFilename();
		try {
			Image image = new Image(imgName,file.getContentType(),file.getBytes());
			return imageRepository.save(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
