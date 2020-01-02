package com.jc.unity.service.impl;

import com.jc.unity.config.MongoConfig;
import com.jc.unity.config.MongoConnectParamConfig;
import com.jc.unity.model.FileMongoDO;
import com.jc.unity.service.FileMongoService;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * File 服务.
 *
 * @author InitUser
 */
@Slf4j
@Service
public class FileMongoServiceImpl implements FileMongoService {

	@Autowired
	private MongoConnectParamConfig mongoConnectParamConfig;

	@Autowired
	MongoConfig mongoConfig;

	@Override
	public String saveFile(MultipartFile file) {
		try {
			long index = file.getSize() % mongoConnectParamConfig.getBucketCollectNum();
			String fileSlot = Long.toString(index + mongoConnectParamConfig.getBucketCollectNum());
			ObjectId objectId = mongoConfig.getGridFsOperations(fileSlot).store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
			final String concat = fileSlot.concat("/").concat(objectId.toString());
			log.debug("bucket[" + file.getOriginalFilename() + "], url: " + concat);
			return concat;
		}catch (Exception e) {
			log.error("[" + file.getOriginalFilename() + "] upload fail: " + e.getMessage());
		}
		return "";
	}

	@Override
	public void removeFile(String fileUrl) {
		String[] strs = fileUrl.split("/");
		this.removeFile(strs[0], strs[1]);
	}

	@Override
	public void removeFile(String fileSlot,String id) {
		Integer slot = Integer.parseInt(fileSlot);
		log.debug(String.format("delete bucket: %d, id: %s", slot, id));
		mongoConfig.getGridFsOperations(fileSlot).delete(query(where("_id").is(id)));
	}

	@Override
	public FileMongoDO getFileById(String fileSlot, String id)
	{
		GridFSDownloadStream gridFSDownloadStream = null;
		FileMongoDO fileMongoDO = null;
		try {
			Integer slot = Integer.parseInt(fileSlot);
			log.debug(String.format("get bucket: %d, id: %s", slot, id));
			gridFSDownloadStream = mongoConfig.getGridFSBucket(fileSlot).openDownloadStream(new ObjectId(id));
			if (null != gridFSDownloadStream) {
				GridFSFile gridFSFile = gridFSDownloadStream.getGridFSFile();
				fileMongoDO = new FileMongoDO(gridFSFile.getFilename(),gridFSFile.getMetadata().getString("_contentType"),
						gridFSFile.getLength(),new Binary(gridFSDownloadStream.readAllBytes()));
			}
		}catch (Exception e) {

			log.error("[" + fileSlot.concat("/").concat(id) + "] getFileByteById fail: " + e.getMessage());

		}finally {

			if (null != gridFSDownloadStream) {
				gridFSDownloadStream.close();
			}

		}
		return fileMongoDO;
	}
}
