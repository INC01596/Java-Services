/*package com.incture.cherrywork.services;





import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.web.multipart.MultipartFile;

public interface SharePointUploadReturnOrderService {

	public String getSharePointAccessToken() throws ClientProtocolException, IOException;

	public String sharePointDelete(String fileName, String returnReqNum, String country)
			throws FileNotFoundException, IOException;

	public byte[] getDocumentInSharePoint(String country, String returnReqNum)
			throws ClientProtocolException, IOException, URISyntaxException;

	String putRecordInSharePoint(File file, String salesOrg) throws ClientProtocolException, IOException;

	public File convertMultiPartToFile(MultipartFile file) throws IOException;
}

*/