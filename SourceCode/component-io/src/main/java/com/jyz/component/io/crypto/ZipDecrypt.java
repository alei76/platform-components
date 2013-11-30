package com.jyz.component.io.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.jyz.component.core.exception.JyzRuntimeException;
import com.jyz.component.io.utils.IOUtils;

/**
 *	@author JoyoungZhang@gmail.com
 *
 */
public class ZipDecrypt {

	/**
	 * 
	 * @param srcFile 要压缩的文件或文件夹，如c:/haha.jpg
	 * @param destfile 压缩后的文件，如c:/加密压缩文件.zip
	 * @param keyfile 公钥存放地点
	 */
	public static void decryptUnzip(String srcfile, String destfile, String keyfile){
		try{
			//1.对文件解密
			File temp = new File(UUID.randomUUID().toString() + ".zip");
			temp.deleteOnExit();
			decrypt(srcfile, temp.getAbsolutePath(), getKey(keyfile));
			//2.解压缩
			unZip(destfile, temp.getAbsolutePath());
			temp.delete();
		}catch(Exception ex){
			throw new JyzRuntimeException(ex);
		}
	}
	
	/**
	 * 把文件srcFile解密后存储为destFile
	 * 
	 * @param srcFile
	 * @param destFile
	 * @param privateKey
	 * @throws Exception
	 */
	private static void decrypt(String srcFile, String destFile, Key privateKey) throws Exception {
		SecureRandom sr = new SecureRandom();
		Cipher ciphers = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec spec = new IvParameterSpec(privateKey.getEncoded());
		ciphers.init(Cipher.DECRYPT_MODE, privateKey, spec, sr);
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		byte[] b = new byte[2064];
		while (fis.read(b) != -1) {
			fos.write(ciphers.doFinal(b));
		}
		IOUtils.closeOutputStream(fos);
		IOUtils.closeInputStream(fis);
	}

	
	/**
	 * 解压缩文件zipFile保存在directory目录下
	 * 
	 * @param directory
	 * @param zipFile
	 * @throws IOException 
	 */
	private static void unZip(String directory, String zipFile) throws IOException {
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
		File f = new File(directory);
		f.mkdirs();
		fileUnZip(zis, f);
		IOUtils.closeInputStream(zis);
	}
	
	/**
	 * 解压缩文件
	 * 
	 * @param zis
	 * @param file
	 * @throws IOException 
	 * @throws Exception
	 */
	private static void fileUnZip(ZipInputStream zis, File file) throws IOException {
		ZipEntry zip = zis.getNextEntry();
		if (zip == null) {
			return;
		}
		String name = zip.getName();
		File f = new File(file.getAbsolutePath() + "/" + name);
		if (zip.isDirectory()) {
			f.mkdirs();
			fileUnZip(zis, file);
		} else {
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			byte[] buffer = new byte[2048];
			int position = 0;
			while ((position = zis.read(buffer)) != -1) {
				fos.write(buffer, 0, position);
			}
			IOUtils.closeOutputStream(fos);
			fileUnZip(zis, file);
		}
	}
	
	/**
	 * 根据key的路径文件获得持久化成文件的key
	 * <P>
	 * 例子: RsaEncrypt.getKey("c:/systemkey/private.key");
	 * 
	 * @param keyPath
	 * @return
	 * @throws IOException 
	 */
	private static Key getKey(String keyPath) throws IOException {
		FileInputStream fis = new FileInputStream(keyPath);
		byte[] b = new byte[16];
		fis.read(b);
		SecretKeySpec dks = new SecretKeySpec(b, "AES");
		fis.close();
		return dks;
	}
	
	public static void main(String args[]) throws Exception {
		long begin = System.currentTimeMillis();
		ZipDecrypt.decryptUnzip("D:\\Crypto\\component-core-1.0.zip", "D:\\", "D:\\Crypto\\component-core-1.0.key");
		System.out.println("解密解压缩时间：" + (System.currentTimeMillis() - begin));
	}

}
