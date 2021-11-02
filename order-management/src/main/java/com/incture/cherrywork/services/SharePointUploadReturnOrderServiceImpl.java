/*package com.incture.cherrywork.services;





import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.CookieHandler;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.io.IOUtils;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.incture.cherrywork.WConstants.DkshConstants;
import com.incture.cherrywork.entities.Attachment;
import com.incture.cherrywork.util.DestinationReaderUtil;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;




@SuppressWarnings("unused")
@Service
public class SharePointUploadReturnOrderServiceImpl implements SharePointUploadReturnOrderService {

	
	public String putRecordInSharePointFile(MultipartFile file) throws ClientProtocolException, IOException 
	{
//		CloseableHttpClient httpClient =  HttpClients.createDefault();
		Pair<String, String> bearerRealmAndRessourceId = getBearerRealmAndRessourceId(httpClient);
	     Token variable declaration 
		
		String bearerRealm = bearerRealmAndRessourceId.getLeft();
        String ressourceId = bearerRealmAndRessourceId.getRight();

        String token = getBearerToken(bearerRealm, ressourceId, httpClient);
	    String token = getSharePointAccessToken();
	     Null or fail check 
	    if (!token.equalsIgnoreCase("OAUTH_FAIL_MESSAGE"))
	    { 
	    	try{
	         Upload path and file name declaration 
	    	System.err.println("file "+file.getName()+ file.getOriginalFilename());
	        String Url_parameter = "add(url='"+file.getOriginalFilename()+"',overwrite=true)";
	        String UPLOAD_FOLDER_URL = "https://dksh.sharepoint.com/sites/ConnectClient/_api/web/GetFolderByServerRelativeUrl"
	        +"('/sites/ConnectClient/TEST/TH/Customer%20Returns')"+"/Files/";
	        String url = UPLOAD_FOLDER_URL + Url_parameter;
	        
	        System.err.println("url = "+url);
	        
	         * NOTE: RcConstants.UPLOAD_FOLDER_URL =
	         * https://<your_domain>.sharepoint.com/_api/web/
	         * GetFolderByServerRelativeUrl('/Shared%20Documents/<FolderName>')/
	         * Files/
	         

	         Building URL 
	        HttpClient client = HttpClientBuilder.create().build();
	        HttpPost post = new HttpPost(url);
	        post.setHeader("Authorization", "Bearer " + token);
	        //post.setHeader("Content-Length", ""+file.getSize());
	       // post.setHeader("X-RequestDigest","");
	         Declaring File Entity 
	      File convFile =   convertMultiPartToFile( file );
	      
	        
	        post.setEntity(new FileEntity(convFile));

	         Executing the post request 
	        HttpResponse response = client.execute(post);
	        System.err.println("Response Code : " + response.getStatusLine().getStatusCode());
System.err.println("response" + response);
	        
	        
	        
	        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()|| response.getStatusLine().getStatusCode() == HttpStatus.ACCEPTED.value())
	        {
	             Returning Success Message 
	        	System.err.println("Success");
	            return "UPLOAD_SUCCESS_MESSAGE";
	        }
	        else
	        {
	        	System.err.println("Wrong");
	             Returning Failure Message 
	            return "UPLOAD_FAIL_MESSAGE";
	        }
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }
	    return token;
	}
	 OAuth2 authentication to get access token 
	public String getSharePointAccessToken() throws ClientProtocolException, IOException
	{
	     Initializing variables 
	    String grant_type = "client_credentials";
	    String client_id = "d151a5d8-eb5b-493b-a11e-261ede6dca97@fe9c4641-3b53-43d0-af72-8f3e64d3aa05";
	    String client_secret = "VdHIyQe6B2+GG9d+IVCKjfO8w75JnxssvUEUX82SOeI=";
	    String resource = "00000003-0000-0ff1-ce00-000000000000/dksh.sharepoint.com@fe9c4641-3b53-43d0-af72-8f3e64d3aa05";
	   String OAUTH_URL = "https://accounts.accesscontrol.windows.net/";
	   String URL_PARAMETER = "fe9c4641-3b53-43d0-af72-8f3e64d3aa05";
	    String url = OAUTH_URL + URL_PARAMETER + "/tokens/OAuth/2";

	    
	     * NOTE: RcConstants.OAUTH_URL =
	     * https://accounts.accesscontrol.windows.net/ RcConstants.URL_PARAMETER
	     * = Bearer Realm from
	     * (http://www.ktskumar.com/2017/01/access-sharepoint-online-using-
	     * postman/) Figure 6.
	     

	     Building URL 
	    HttpClient client = HttpClientBuilder.create().build();
	    HttpPost post = new HttpPost(url);
	    post.setHeader("Content-Type", "application/x-www-form-urlencoded");

	     Adding URL Parameters 
	    List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
	    urlParameters.add(new BasicNameValuePair("grant_type", grant_type));
	    urlParameters.add(new BasicNameValuePair("client_id", client_id));
	    urlParameters.add(new BasicNameValuePair("client_secret", client_secret));
	    urlParameters.add(new BasicNameValuePair("resource", resource));
	    post.setEntity(new UrlEncodedFormEntity(urlParameters));

	     Executing the post request 
	    HttpResponse response = client.execute(post);
	    System.err.println("Response Code : " + response.getStatusLine().getStatusCode());

	    String json_string = EntityUtils.toString(response.getEntity());
	    JSONObject temp1 = new JSONObject(json_string);  
	    if (temp1 != null)
	    {
	         Returning access token 
	    	System.err.println("temp1 "+temp1);
	        return temp1.get("access_token").toString();
	    }else{
	    return "403 Failed to get access token";
	    }
	}
	 private Pair<String, String> getBearerRealmAndRessourceId(CloseableHttpClient httpClient) {
	        // domain = mysharepoint.sharepoint.com
	        String url ="https://dksh.sharepoint.com/sites/ConnectClient/_layouts/15/sharepoint.aspx";

	        HttpGet getRequest = new HttpGet(url);
	        getRequest.setHeader("Authorization", "Bearer");

	        try {
	            HttpResponse response = httpClient.execute(getRequest);
	            Header[] headers = response.getHeaders("www-authenticate");

	            String bearerRealm = extractHeaderElement(headers, "Bearer realm");
	            String ressourceId = extractHeaderElement(headers, "client_id");
	            return Pair.of(bearerRealm, ressourceId);
	        } catch (IOException e) {
	            throw new RuntimeException("Get Request zum Holen von Bearer realm und client_id fehlgeschlagen", e);
	        }
	    }

	    private String extractHeaderElement(Header[] headers, String elementName) {
	    	
	  
	    	
	        return Arrays.asList(headers).stream()
	                .map(header -> header.getElements())
	                .flatMap(elements -> Arrays.asList(elements).stream())
	                .filter(element -> element.getName().equals(elementName))
	                .findFirst()
	                .orElseThrow(null)
	                .getValue();
	    }
	    
	    private String getBearerToken(String bearerRealm, String ressourceId, CloseableHttpClient httpClient) {
	        String url = String.format("https://accounts.accesscontrol.windows.net/%s/tokens/OAuth/2", bearerRealm);

	        HttpPost postRequest = new HttpPost(url);
	        postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");

	        String clientId = String.format("%s@%s","d151a5d8-eb5b-493b-a11e-261ede6dca97", bearerRealm);
	        String resource = String.format("%s/%s@%s", ressourceId,"dksh.sharepoint.com", bearerRealm);
	        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
	        urlParameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
	        urlParameters.add(new BasicNameValuePair("client_id", clientId));
	        urlParameters.add( new BasicNameValuePair("client_secret","VdHIyQe6B2+GG9d+IVCKjfO8w75JnxssvUEUX82SOeI="));
	        urlParameters.add(new BasicNameValuePair("resource", resource));

	        try {
	            postRequest.setEntity(new UrlEncodedFormEntity(urlParameters));
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException("Parameter falsch formatiert", e);
	        }

	        try  {
	            HttpResponse response = httpClient.execute(postRequest);

	            @SuppressWarnings("deprecation")
				String bodyJson = IOUtils.toString(response.getEntity().getContent());
	            JSONObject body = new JSONObject(bodyJson);
	            String bearerToken = body.getString("access_token");
	            return bearerToken;
	        } catch (IOException e) {
	            throw new RuntimeException("Post Request zum Holen des Bearer Tokens fehlgeschlagen", e);
	        }
	    }
	
	
	
	
	@Override
	  public File convertMultiPartToFile(MultipartFile file ) throws IOException
	    {
	        File convFile = new File( file.getOriginalFilename() );
	        FileOutputStream fos = new FileOutputStream( convFile );
	        fos.write( file.getBytes() );
	        fos.close();
	        return convFile;
	    }
	 
	 private File convertByteArrayToFile(String fileName,String returnReqNum,List<Attachment> attachement) throws IOException{
		 
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
 	    ZipOutputStream zos = new ZipOutputStream(baos);
       for(int i=0;i<attachement.size();i++){       
     	        ZipEntry entry = new ZipEntry(returnReqNum +"-"+attachement.get(i).getDocName());
     	        entry.setSize(attachement.get(i).getDocData().length);
     	        zos.putNextEntry(entry);
     	        zos.write(attachement.get(i).getDocData());
       }
       zos.closeEntry();
	    zos.close();
		 
		 File convFile = new File(fileName);
		 
	        FileOutputStream fos = new FileOutputStream( convFile );
	       
	        fos.write(baos.toByteArray());
	        fos.close();
	        return convFile;
		 
	 }
	 
	 
	 

		public static Pair<String, String> login(String username, String password, String domain) {
			
			Pair<String, String> result;
			String token;
			try {
				token = requestToken(domain, username, password);
				if (token == null) {
					return null;
				}
				result = submitToken(domain, token);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		private static String requestToken(String domain, String username, String password) throws XPathExpressionException, SAXException, ParserConfigurationException, IOException {
			String saml = generateSAML(domain, username, password);
			String sts = "https://login.microsoftonline.com/extSTS.srf";
			URL u = new URL(sts);
			URLConnection uc = u.openConnection();
			HttpURLConnection connection = (HttpURLConnection) uc;

			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.addRequestProperty("Content-Type", "text/xml; charset=utf-8");
			OutputStream out = connection.getOutputStream();
			Writer writer = new OutputStreamWriter(out);
			writer.write(saml);

			writer.flush();
			writer.close();

			InputStream in = connection.getInputStream();
			int c;
			StringBuilder sb = new StringBuilder("");
			while ((c = in.read()) != -1) {
				sb.append((char) (c));
			}
			in.close();
			String result = sb.toString();
			String token = extractToken(result);
			if (token == null || token.equals("")) {
				System.err.println("Login failed : " + result);
				return null;
			}
			return token;
		}

		private static String generateSAML(String domain, String username, String password) {
			String reqXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
					+ "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:a=\"http://www.w3.org/2005/08/addressing\" xmlns:u=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n"
					+ "   <s:Header>\n"
					+ "      <a:Action s:mustUnderstand=\"1\">http://schemas.xmlsoap.org/ws/2005/02/trust/RST/Issue</a:Action>\n"
					+ "      <a:ReplyTo>\n"
					+ "         <a:Address>http://www.w3.org/2005/08/addressing/anonymous</a:Address>\n"
					+ "      </a:ReplyTo>\n"
					+ "      <a:To s:mustUnderstand=\"1\">https://login.microsoftonline.com/extSTS.srf</a:To>\n"
					+ "      <o:Security xmlns:o=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" s:mustUnderstand=\"1\">\n"
					+ "         <o:UsernameToken>\n"
					+ "            <o:Username>[[username]]</o:Username>\n"
					+ "            <o:Password>[[password]]</o:Password>\n"
					+ "         </o:UsernameToken>\n"
					+ "      </o:Security>\n"
					+ "   </s:Header>\n"
					+ "   <s:Body>\n"
					+ "      <t:RequestSecurityToken xmlns:t=\"http://schemas.xmlsoap.org/ws/2005/02/trust\">\n"
					+ "         <wsp:AppliesTo xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\">\n"
					+ "            <a:EndpointReference>\n"
					+ "               <a:Address>[[endpoint]]</a:Address>\n"
					+ "            </a:EndpointReference>\n"
					+ "         </wsp:AppliesTo>\n"
					+ "         <t:KeyType>http://schemas.xmlsoap.org/ws/2005/05/identity/NoProofKey</t:KeyType>\n"
					+ "         <t:RequestType>http://schemas.xmlsoap.org/ws/2005/02/trust/Issue</t:RequestType>\n"
					+ "         <t:TokenType>urn:oasis:names:tc:SAML:1.0:assertion</t:TokenType>\n"
					+ "      </t:RequestSecurityToken>\n"
					+ "   </s:Body>\n"
					+ "</s:Envelope>";
			String saml = reqXML.replace("[[username]]", username);
			saml = saml.replace("[[password]]", password);
			saml = saml.replace("[[endpoint]]", "https://dksh.sharepoint.com/_forms/default.aspx?wa=wsignin1.0");
			return saml;
		}

		private static String extractToken(String result) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new InputSource(new StringReader(result)));
			XPathFactory xpf = XPathFactory.newInstance();
			XPath xp = xpf.newXPath();
			String token = xp.evaluate("//BinarySecurityToken/text()", document.getDocumentElement());
			return token;
		}

		private static Pair<String, String> submitToken(String domain, String token) throws IOException {
			String loginContextPath = "/_forms/default.aspx?wa=wsignin1.0";
			String url = String.format("https://%s.sharepoint.com%s", domain, loginContextPath);
//			logger.info("url=" + url);
//			logger.info("token2=" + token);
//			logger.info("java.version=" + System.getProperty("java.version"));
			CookieHandler.setDefault(null);
			URL u = new URL(url);
			URLConnection uc = u.openConnection();
			HttpURLConnection connection = (HttpURLConnection) uc;
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.addRequestProperty("Accept", "application/x-www-form-urlencoded");
			//connection.addRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)");
			connection.addRequestProperty("Content-Type", "text/xml; charset=utf-8");
			connection.setInstanceFollowRedirects(false);
			OutputStream out = connection.getOutputStream();
			Writer writer = new OutputStreamWriter(out);
			writer.write(token);
			writer.flush();
			out.flush();
			writer.close();
			out.close();

			String rtFa = null;
			String fedAuth = null;
			Map<String, List<String>> headerFields = connection.getHeaderFields();
			List<String> cookiesHeader = headerFields.get("Set-Cookie");
			if (cookiesHeader != null) {
				for (String cookie : cookiesHeader) {
					if (cookie.startsWith("rtFa=")) {
						rtFa = "rtFa=" + HttpCookie.parse(cookie).get(0).getValue();
					} else if (cookie.startsWith("FedAuth=")) {
						fedAuth = "FedAuth=" + HttpCookie.parse(cookie).get(0).getValue();
					} else {
						//logger.info("waste=" + HttpCookie.parse(cookie).get(0).getValue());
					}
				}
			}
			
			InputStream in = connection.getInputStream();
			for (int i = 0;; i++) {
				String headerName = connection.getHeaderFieldKey(i);
				String headerValue = connection.getHeaderField(i);
				System.out.println("\t\theaderName=" + headerName + "=" + headerValue);
				if (headerName == null && headerValue == null) {
					break;
				}
				if (headerName == null) {
				} else {
					if (headerName.equals("Set-Cookie") && headerValue.startsWith("rtFa=")) {
						rtFa = headerValue;
					} else if (headerName.equals("Set-Cookie") && headerValue.startsWith("FedAuth=")) {
						fedAuth = headerValue;
					}
				}
			}
			 
//			logger.info("rtFa=" + rtFa);
//			logger.info("fedAuth=" + fedAuth);

			Pair<String, String> result = ImmutablePair.of(rtFa, fedAuth);
//			System.out.println("loginResult=" + IOUtils.toString(in, "utf-8"));

			return result;
		}

		public static String contextinfo(Pair<String, String> token, String domain) {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			try {
				HttpPost getRequest = new HttpPost("https://" + domain + ".sharepoint.com/_api/contextinfo");
				getRequest.addHeader("Cookie", token.getLeft() + ";" + token.getRight());
				getRequest.addHeader("accept", "application/json;odata=verbose");
				HttpResponse response = httpClient.execute(getRequest);
				if (response.getStatusLine().getStatusCode() == 200) {
					return IOUtils.toString(response.getEntity().getContent(), "utf-8");
				} else {
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					httpClient.close();
				} catch (IOException ex) {
				}
			}
			return null;
		}

		public static String get(Pair<String, String> token, String url) {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			try {
				HttpGet getRequest = new HttpGet(url);
				getRequest.addHeader("Cookie", token.getLeft() + ";" + token.getRight());
				getRequest.addHeader("accept", "application/json;odata=verbose");
				HttpResponse response = httpClient.execute(getRequest);
				if (response.getStatusLine().getStatusCode() == 200) {
					return IOUtils.toString(response.getEntity().getContent(), "utf-8");
				} else {
					System.err.println("Failed : HTTP error code : " + response.getStatusLine().getStatusCode() + ", " + url);
					return null;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					httpClient.close();
				} catch (IOException ex) {
				}
			}
			return null;
		}

		public static String get(Pair<String, String> token, String domain, String path) {
			return get(token, "https://" + domain + ".sharepoint.com/" + path);
		}

		public static String post(Pair<String, String> token, String domain, String path, String data, String formDigestValue,MultipartFile file) {
			return post(token, domain, path, data, formDigestValue, false,file);
		}

		public static String post(Pair<String, String> token, String domain, String path, String data, String formDigestValue, boolean isXHttpMerge,MultipartFile file) {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			try {
				HttpPost postRequest = new HttpPost("https://" + domain + ".sharepoint.com" + path);
				postRequest.addHeader("Cookie", token.getLeft() + ";" + token.getRight());
				postRequest.addHeader("accept", "application/json;odata=verbose");
				postRequest.addHeader("content-type", "application/json;odata=verbose");
				postRequest.addHeader("X-RequestDigest", formDigestValue);
				postRequest.addHeader("IF-MATCH", "*");
				if (isXHttpMerge) {
					postRequest.addHeader("X-HTTP-Method", "MERGE");
				}

				List<NameValuePair> nvps = new ArrayList<>();
				if (data != null) {
					StringEntity input = new StringEntity(data, "UTF-8");
					input.setContentType("application/json");
					postRequest.setEntity(input);
				}

				HttpResponse response = httpClient.execute(postRequest);
				if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
					System.err.println("Failed : HTTP error code : " + response.getStatusLine().getStatusCode() + ", " + path);
				}
				if (response.getEntity() == null || response.getEntity().getContent() == null) {
					return null;
				} else {
					return IOUtils.toString(response.getEntity().getContent(), "utf-8");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					httpClient.close();
				} catch (IOException ex) {
				}
			}
			return null;
		}
		
		public  String postFile(Pair<String, String> token, String domain, String path, String data, String formDigestValue, boolean isXHttpMerge,MultipartFile file) {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			try {
				
				 String Url_parameter = "add(url='"+file.getOriginalFilename()+"',overwrite=true)";
			        String UPLOAD_FOLDER_URL = "https://dksh.sharepoint.com/sites/ConnectClient/_api/Web/"+"GetFolderByServerRelativeUrl"
			        +"('sites/ConnectClient/TEST')"+"/Files/";
			        String url = UPLOAD_FOLDER_URL + Url_parameter;
				HttpPost postRequest = new HttpPost(url);
				postRequest.addHeader("Cookie", token.getLeft() + ";" + token.getRight());
				postRequest.addHeader("accept", "application/json;odata=verbose");
				postRequest.addHeader("content-type", "application/json;odata=verbose");
				postRequest.addHeader("X-RequestDigest", formDigestValue);
				postRequest.addHeader("IF-MATCH", "*");
				if (isXHttpMerge) {
					postRequest.addHeader("X-HTTP-Method", "MERGE");
				}

				List<NameValuePair> nvps = new ArrayList<>();
				if (file != null) {
					  File convFile =   convertMultiPartToFile( file );
				        
					  postRequest.setEntity(new FileEntity(convFile));
				}
				
				

				HttpResponse response = httpClient.execute(postRequest);
				if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
					System.err.println("Failed : HTTP error code : " + response.getStatusLine().getStatusCode() + ", " + path);
				}
				if (response.getEntity() == null || response.getEntity().getContent() == null) {
					return null;
				} else {
					return IOUtils.toString(response.getEntity().getContent(), "utf-8");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					httpClient.close();
				} catch (IOException ex) {
				}
			}
			return null;
		}

		public static String delete(Pair<String, String> token, String domain, String path, String formDigestValue) {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			try {
				HttpDelete deleteRequest = new HttpDelete("https://" + domain + ".sharepoint.com/" + path);
				deleteRequest.addHeader("Cookie", token.getLeft() + ";" + token.getRight());
				deleteRequest.addHeader("accept", "application/json;odata=verbose");
				deleteRequest.addHeader("content-type", "application/json;odata=verbose");
				deleteRequest.addHeader("X-RequestDigest", formDigestValue);
				deleteRequest.addHeader("IF-MATCH", "*");
				HttpResponse response = httpClient.execute(deleteRequest);
				if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
					System.err.println("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}
				if (response.getEntity() == null || response.getEntity().getContent() == null) {
					return null;
				} else {
					return IOUtils.toString(response.getEntity().getContent(), "utf-8");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					httpClient.close();
				} catch (IOException ex) {
System.err.println("error "+ex);				}
			}
			return null;
		}
		@Override
		public String sharePointDelete(String fileName , String returnReqNum, String country) throws FileNotFoundException, IOException {
			String username = "sap.fiori@dksh.com";
			String password = "Mnbvcxz1";
			
			String domain = "dksh";
			Pair<String, String> token = login(username, password, domain);
			if (token != null) {
				String jsonString = post(token, domain, "/_api/contextinfo", null, null,null);
				System.err.println(jsonString);
				JSONObject json = new JSONObject(jsonString);
				String formDigestValue = json.getJSONObject("d").getJSONObject("GetContextWebInformation").getString("FormDigestValue").toString();
				System.err.println("FormDigestValue=" + formDigestValue);
				// delete list john
				jsonString = delete(token, domain, "_api/web/GetFileByServerRelativeUrl('/sites/ConnectClient/TEST/TH/Customer%20Returns/XCR20102210-0008.zip/XCR20102210-0008-(42)T007.2.PNG')", formDigestValue);
				if (jsonString != null) {
					
					System.err.println(jsonString);
					return jsonString;
				}
			} 
				System.err.println("Login failed");
				return "Login failed";
			
		}
		@Override
		public byte[] getDocumentInSharePoint(String returnReqNum,String country) throws ClientProtocolException, IOException, URISyntaxException		{
			CloseableHttpClient httpClient =  HttpClients.createDefault();
			Pair<String, String> bearerRealmAndRessourceId = getBearerRealmAndRessourceId(httpClient);
		     Token variable declaration 
			
			String bearerRealm = bearerRealmAndRessourceId.getLeft();
	        String ressourceId = bearerRealmAndRessourceId.getRight();

	        String token = getBearerToken(bearerRealm, ressourceId, httpClient);
		    String token = getSharePointAccessToken();
		     Null or fail check 
		    if (!token.equalsIgnoreCase("OAUTH_FAIL_MESSAGE"))
		    { 
		         Upload path and file name declaration 
		    	Map<String, Object> map = DestinationReaderUtil.getDestination(DkshConstants.SHAREPOINTURL);
		    	map.get("URL");
		        String Url_parameter = "/Files('"+returnReqNum+".zip"+"')/$value";
		        String UPLOAD_FOLDER_URL = map.get("URL")+country+"/Customer%20Returns')";
		        String url = UPLOAD_FOLDER_URL + Url_parameter;
		        
		        System.err.println(" sharepoint map url = "+url);
		        
		        RestTemplate restTemplate = new RestTemplate();
		        
		        final String baseUrl = url;
		        
		        URI uri = null;
				try {
					uri = new URI(baseUrl);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        HttpHeaders setheaders = new HttpHeaders();
		        setheaders.set("Authorization", "Bearer " + token);
		        setheaders.set("Accept","application/json;odata=verbose");
		        
		        HttpEntity<?> requestEntity = new HttpEntity<>(null, setheaders);
		        
		        ResponseEntity<byte []> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, byte[].class);
 
		       System.err.println( "result "+result.getBody());
		       
		       byte[] responseStrFromSharePoint = result.getBody();
		      
		     
		        
		         Building URL 
		        HttpClient client = HttpClientBuilder.create().build();
		        HttpGet get = new HttpGet(url);
		        get.setHeader("Authorization", "Bearer " + token);
		        get.setHeader("Accept","application/json;odata=verbose");
		       
		         Executing the post request 
		        HttpResponse response = client.execute(get);
		        System.err.println("Response Code : " + response.getStatusLine().getStatusCode());
	            System.err.println("response" + response.getEntity());
	            
		        if(result.getStatusCodeValue()== HttpStatus.OK.value())
		        
		       // if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()|| response.getStatusLine().getStatusCode() == HttpStatus.ACCEPTED.value())
		        {
		             Returning Success Message 
		        	System.err.println("Success");
		            return responseStrFromSharePoint;
		        }
		        else
		        {
		        	System.err.println("Wrong");
		             Returning Failure Message 
		            return responseStrFromSharePoint;
		        }
		    }
		    return null;
		}
		@Override
		public String putRecordInSharePoint(File file,String salesOrg) throws ClientProtocolException, IOException {

			String token = getSharePointAccessToken();
			
			
			 Null or fail check 
			if (!token.equalsIgnoreCase("OAUTH_FAIL_MESSAGE")) {
				try{
					Map<String, Object> map = DestinationReaderUtil.getDestination(DkshConstants.SHAREPOINTURL);
			    	//map.get("URL");
			    	
			    	System.err.println("inside put record");
				 Upload path and file name declaration 
				System.err.println("file " + file.getName());
				String Url_parameter = "add(url='" + file.getName() + ".zip" + "',overwrite=true)";
				String UPLOAD_FOLDER_URL = map.get("URL")+salesOrg+"/Customer%20Returns')" + "/Files/";
				String url = UPLOAD_FOLDER_URL + Url_parameter;

				System.err.println("url = " + url);
			

				 Building URL 
				HttpClient client = HttpClientBuilder.create().build();
				HttpPost post = new HttpPost(url);
				post.setHeader("Authorization", "Bearer " + token);
				

				post.setEntity(new FileEntity(file));

				 Executing the post request 
				HttpResponse response = client.execute(post);
				System.err.println("Response Code : " + response.getStatusLine().getStatusCode());
				System.err.println("response" + response);

				if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()
						|| response.getStatusLine().getStatusCode() == HttpStatus.ACCEPTED.value()) {
					 Returning Success Message 
					System.err.println("Success");
					
					return "UPLOAD_SUCCESS_MESSAGE";
				} else {
					System.err.println("Wrong");
					 Returning Failure Message 
					
					
				}
			}catch(Exception e ){
				System.err.println("exception"+ e);
				return "upload failed due to "+ e;
			}
		}
			return token;
		
		
		
		
	}
		
		
}



*/