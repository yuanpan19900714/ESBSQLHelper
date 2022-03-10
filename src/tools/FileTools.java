package tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileTools {

	public static byte[] readContent(String fileName) {
		byte[] value = null;
		try {
			InputStream input = new FileInputStream(fileName);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] cache = new byte[100];
			int len = -1;
			while ((len = input.read(cache)) != -1) {
				baos.write(cache, 0, len);
			}
			value = baos.toByteArray();
			input.close();
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void writeFile(String path, byte[] value) {
		try {
			OutputStream out = new FileOutputStream(new File(path));

			out.write(value);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
