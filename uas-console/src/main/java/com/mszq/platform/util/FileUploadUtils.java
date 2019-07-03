package com.mszq.platform.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TryCatchFinally;
import javax.swing.text.AbstractDocument.LeafElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtils {

	private static Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);

	private FileUploadUtils() {
	}

	public static String FilesUploadTransferToSpring(HttpServletRequest request, MultipartFile multipartFile,
			String filePath) {
		if (multipartFile != null) {
			String suffix = multipartFile.getOriginalFilename()
					.substring(multipartFile.getOriginalFilename().lastIndexOf("."));
			String absolutePath = getAndSetAbsolutePath(request, filePath, suffix);
			String relativePath = getRelativePath(filePath, suffix);
			try {
				multipartFile.transferTo(new File(absolutePath));
				return relativePath;
			} catch (IOException e) {
				logger.error("",e);
				return null;
			}
		} else
			return null;
	}

	public static void FilesUploadStream(MultipartFile multipartFile, String filePath) {
		if (multipartFile != null) {
//			String suffix = multipartFile.getOriginalFilename()
//					.substring(multipartFile.getOriginalFilename().lastIndexOf("."));
			String fileName = multipartFile.getOriginalFilename();
//			String absolutePath = getAbsolutePath(request, filePath, fileName);
//			String relativePath = getRelativePath(filePath, fileName);
			checkDir(filePath);
			InputStream inputStream = null;
			BufferedOutputStream fileOutputStream = null;
			try {
				inputStream = multipartFile.getInputStream();
				fileOutputStream = new BufferedOutputStream(new FileOutputStream(filePath + fileName));
				byte buffer[] = new byte[4096]; 
				long fileSize = multipartFile.getSize();
				if (fileSize <= buffer.length) {
					buffer = new byte[(int) fileSize];
				}
				int line = 0;
				while ((line = inputStream.read(buffer, 0, buffer.length)) > 0) {
					fileOutputStream.write(buffer, 0, line);
				}
			} catch (Exception e) {
				logger.error("",e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
				throw new RuntimeException("写入文件失败");
			}finally {
				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();// 关闭输出流
					} catch (IOException e) {
						logger.error("", e);
					}
				}
				if (inputStream != null) {
					try {
						inputStream.close();// 关闭输入流
					} catch (IOException e) {
						logger.error("", e);
					}
				}
			}
		}
	}

	/**
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param filePath
	 *            example "/filesOut/Download/mst.txt"
	 * @return
	 * @throws IOException 
	 */
	public static void FilesDownloadStream(HttpServletRequest request, HttpServletResponse response, String filePath,boolean isDelete) throws IOException {
//		String realPath = request.getSession().getServletContext().getRealPath(filePath);
//		File file = new File(realPath);
		File file = new File(filePath);
		String filenames = file.getName();
		
//		
//		File dir = new File(uploadUrl);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        
//        File targetFile = new File(uploadUrl + filename);
//        if (!targetFile.exists()) {
//            try {
//                targetFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

		
		
		InputStream bis = null;
		OutputStream bos = null;
		try {
			response.reset();
			// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(filenames.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
			response.setHeader("Content-Length", "" + file.length());
			response.setContentType("application/octet-stream");
			// response.setContentType("application/x-msdownload;");
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while ((bytesRead = bis.read(buff, 0, buff.length)) > 0) {
				bos.write(buff, 0, bytesRead);
			}
			
		} catch (Exception e) {
			logger.error("", e);
			response.sendRedirect("/platform/app/error.html");
//			throw new RuntimeException(e);
		} finally {
			if (bos != null) {
				try {
					bos.flush();
					bos.close();// 关闭输出流
				} catch (IOException e) {
					logger.error("", e);
				}
			}
			if (bis != null) {
				try {
					bis.close();// 关闭输入流
				} catch (IOException e) {
					logger.error("", e);
				}
			}
			 if(isDelete)
             {
                 file.delete();        //是否将生成的服务器端文件删除
             }
		}
	}

	// -------------------------------------------------------------------------------
	public static String getServerPath(HttpServletRequest request, String filePath) {
		return request.getSession().getServletContext().getRealPath(filePath);
	}

	public static String getDataPath() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	public static void checkDir(String savePath) {
		boolean res = false;
		File dir = new File(savePath);
		if (!dir.exists() && !dir.isDirectory()) {
			res = dir.mkdirs();
		}
	}

	public static String getUUIDName(String suffix) {
		return UUID.randomUUID().toString() + suffix;
	}

	public static String getAndSetAbsolutePath(HttpServletRequest request, String filePath, String suffix) {
		String savePath = getServerPath(request, filePath) + File.separator + getDataPath() + File.separator;
		checkDir(savePath);
		return savePath + getUUIDName(suffix);
	}
	
	public static String getAbsolutePath(HttpServletRequest request, String filePath, String fileName) {
		String savePath = getServerPath(request, filePath) + File.separator  + File.separator;
		checkDir(savePath);
		return savePath + fileName;
	}

	public static String getRelativePath(String filePath, String suffix) {
		return filePath + File.separator + getDataPath() + File.separator + suffix;
	}

	public static String downloadFiles(List<String> filePaths, HttpServletRequest request, HttpServletResponse response) throws IOException
			 {
		List<File> files = new ArrayList<File>();
		for (String string : filePaths) {
			File file = new File(string);
			if(file.exists()){
				files.add(file);
			}else {
				logger.info("批量下载选择文件不存在："+ string);
			}
		}
		if (files.size() == 0) {
			logger.info("批量下载选择文件全部不存在，返回错误页面");
			response.sendRedirect("/platform/app/error.html");
			return null;
		}
		//必须为不重复的名字，否则多处同时下载会出现临时文件相互冲突覆盖
		String fileName = UUID.randomUUID().toString() + ".zip";
		// 创建打包下载的临时文件
		String outFilePath = GlobalConfig.getConfgiValue("reportFileRootPath");

		File fileZip = new File(outFilePath + fileName);
		// 文件输出流
		FileOutputStream outStream = null;
		// 压缩流
		ZipOutputStream toClient = null;
		try {
			outStream = new FileOutputStream(fileZip);
			toClient = new ZipOutputStream(outStream);
			// toClient.setEncoding("gbk");
			zipFile(files, toClient);
			FilesDownloadStream(null, response, outFilePath + fileName, true);
		} catch (Exception e) {
			logger.error("", e);
			response.sendRedirect("/platform/app/error.html");
		} finally {
			if (outStream != null) {
				try {
					outStream.flush();
					outStream.close();
				} catch (IOException e) {
//					logger.error("", e);
				}
			}
			if (toClient != null) {
				try {
					toClient.flush();
					toClient.close();
				} catch (IOException e) {
//					logger.error("", e);
				}
			}
			fileZip.delete();
		}

		return null;
	}

	public static void zipFile(List<File> files, ZipOutputStream outputStream) throws IOException {
		int size = files.size();
		// 压缩列表中的文件
		for (int i = 0; i < size; i++) {
			File file = (File) files.get(i);
			zipFile(file, outputStream);
		}
	}

	public static void zipFile(File inputFile, ZipOutputStream outputstream) throws IOException {
		FileInputStream inStream = null;
		BufferedInputStream bInStream = null;
		try {
			if (inputFile.exists()) {
				if (inputFile.isFile()) {
					inStream = new FileInputStream(inputFile);
					bInStream = new BufferedInputStream(inStream);
					ZipEntry entry = new ZipEntry(inputFile.getName());
					outputstream.putNextEntry(entry);

					final int MAX_BYTE = 10 * 1024 * 1024; // 最大的流为10M
					long streamTotal = 0; // 接受流的容量
					int streamNum = 0; // 流需要分开的数量
					int leaveByte = 0; // 文件剩下的字符数
					byte[] inOutbyte; // byte数组接受文件的数据

					streamTotal = bInStream.available(); // 通过available方法取得流的最大字符数
					streamNum = (int) Math.floor(streamTotal / MAX_BYTE); // 取得流文件需要分开的数量
					leaveByte = (int) streamTotal % MAX_BYTE; // 分开文件之后,剩余的数量

					if (streamNum > 0) {
						for (int j = 0; j < streamNum; ++j) {
							inOutbyte = new byte[MAX_BYTE];
							// 读入流,保存在byte数组
							bInStream.read(inOutbyte, 0, MAX_BYTE);
							outputstream.write(inOutbyte, 0, MAX_BYTE); // 写出流
						}
					}
					// 写出剩下的流数据
					inOutbyte = new byte[leaveByte];
					bInStream.read(inOutbyte, 0, leaveByte);
					outputstream.write(inOutbyte);
					outputstream.closeEntry(); // Closes the current ZIP entry
					// and positions the stream for
					// writing the next entry
				}
			} else {
				throw new RuntimeException("文件不存在！");
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (bInStream != null) {
				try {
					bInStream.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
	}
}
