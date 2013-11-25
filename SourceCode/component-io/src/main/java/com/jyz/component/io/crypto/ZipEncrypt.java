package com.jyz.component.io.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.jyz.component.core.exception.JyzRuntimeException;
import com.jyz.component.io.utils.IOUtils;


/**
 *	@author JoyoungZhang@gmail.com
 *
 */
public class ZipEncrypt {
	
	/**
	 * 
	 * @param srcFile 要压缩的文件或文件夹，如c:/haha.jpg
	 * @param destfile 压缩后的文件，如c:/加密压缩文件.zip
	 * @param keyfile 公钥存放地点
	 * @throws Exception
	 */
	public static void encryptZip(String srcFile, String destfile, String keyfile){
		try{
			SecureRandom sr = new SecureRandom();
			KeyGenerator kg = KeyGenerator.getInstance("AES");
			kg.init(128, sr);
			SecretKey key = kg.generateKey();
			File f = new File(keyfile);
			if (!f.getParentFile().exists()){
				f.getParentFile().mkdirs();
			}
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(key.getEncoded());
			File temp = new File(UUID.randomUUID().toString() + ".zip");
			temp.deleteOnExit();
			//1.压缩文件
			fileZip(srcFile, temp.getAbsolutePath());
			//2.对文件加密
			encrypt(temp.getAbsolutePath(), destfile, key);
			temp.delete();
			IOUtils.closeOutputStream(fos);
		}catch(Exception ex){
			throw new JyzRuntimeException(ex);
		}
	}
	
	/**
	 * 对directory目录下的文件压缩，保存为指定的文件zipFile
	 * 
	 * @param directory
	 * @param zipFile
	 * @throws Exception 
	 */
	private static void fileZip(String path, String zipFile) throws IOException {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
		File file = new File(path);
		if (file.isFile()) {
			zos.putNextEntry(new ZipEntry(file.getName()));
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int position = 0;
			while ((position = fis.read(buffer)) != -1) {
				zos.write(buffer, 0, position);
			}
			IOUtils.closeInputStream(fis);
		} else {
			directoryZip(zos, file, "");
		}
		IOUtils.closeOutputStream(zos);
	}
	
	
	private static void directoryZip(ZipOutputStream out, File f, String base) throws IOException {
		// 如果传入的是目录
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			// 创建压缩的子目录
			out.putNextEntry(new ZipEntry(base + "/"));
			if (base.length() == 0) {
				base = "";
			} else {
				base = base + "/";
			}
			for (int i = 0; i < fl.length; i++) {
				directoryZip(out, fl[i], base + fl[i].getName());
			}
		} else {
			// 把压缩文件加入rar中
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			byte[] buffer = new byte[1024];
			int position = 0;
			while ((position = in.read(buffer)) != -1) {
				out.write(buffer, 0, position);
			}
			IOUtils.closeInputStream(in);
		}
	}
	
	/**
	 * 把文件srcFile加密后存储为destFile
	 * @param srcFile
	 * @param destFile
	 * @param privateKey
	 * @throws Exception
	 */
	private static void encrypt(String srcFile, String destFile, Key privateKey) throws Exception {
		SecureRandom sr = new SecureRandom();
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec spec = new IvParameterSpec(privateKey.getEncoded());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey, spec, sr);
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		byte[] b = new byte[2048];
		while (fis.read(b) != -1) {
			fos.write(cipher.doFinal(b));
		}
		IOUtils.closeOutputStream(fos);
		IOUtils.closeInputStream(fis);
	}
	
	public static void main(String args[]) throws Exception {
		long begin = System.currentTimeMillis();
		ZipEncrypt.encryptZip("F:\\手机号码.jar", "F:\\手机号码加密.zip", "F:\\手机号码.key");
		System.out.println("压缩加密时间：" + (System.currentTimeMillis() - begin));
	}

}
