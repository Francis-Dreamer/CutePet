import java.io.*;

public class test {

	static String oldFilePath = "../res/values/dimens.xml";// 720*1280

	static String filePath540_960 = "../res/values-sw540dp/dimens.xml";

	static float changes = 2 / 1.5f;

	public static void main(String[] args) {
		String allPx = getAllPx();

		DeleteFolder(oldFilePath);

		writeFile(oldFilePath, allPx);

		String st = convertStreamToString(oldFilePath, changes);

		DeleteFolder(filePath540_960);

		writeFile(filePath540_960, st);

	}

	public static String convertStreamToString(String filepath, float f) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader bf = new BufferedReader(new FileReader(filepath));
			String line = null;
			System.out.println("q1");
			String endmark = "px</dimen>";
			String startmark = ">";
			while ((line = bf.readLine()) != null) {
				if (line.contains(endmark)) {
					int end = line.lastIndexOf(endmark);
					int start = line.indexOf(startmark);
					String stpx = line.substring(start + 1, end);
					int px = Integer.parseInt(stpx);
					int newpx = (int) ((float) px / f);
					String newline = line.replace(px + "px", newpx + "px");
					sb.append(newline + "\r\n");
				} else {
					sb.append(line + "\r\n");
				}
			}
			System.out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static boolean DeleteFolder(String sPath) {

		File file = new File(sPath);

		if (!file.exists()) {

			return true;

		} else {

			if (file.isFile()) {

				return deleteFile(sPath);

			} else {

			}

		}

		return false;

	}

	public static void writeFile(String filepath, String st) {
		File file = new File(filepath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		try {

			FileWriter fw = new FileWriter(filepath);

			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(st);

			bw.flush();

			bw.close();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public static String getAllPx() {

		StringBuilder sb = new StringBuilder();

		try {

			sb.append("<resources>" + "\r\n");

			sb.append("<dimen name=\"screen_width\">1920px</dimen>" + "\r\n");

			sb.append("<dimen name=\"screen_height\">1080px</dimen>" + "\r\n");

			for (int i = 1; i <= 1920; i++) {

				System.out.println("i=" + i);

				sb.append("<dimen name=\"px" + i + "\">" + i + "px</dimen>"

				+ "\r\n");

			}

			sb.append("</resources>" + "\r\n");

			System.out.println(sb.toString());

		} catch (Exception e) {

			e.printStackTrace();

		}

		return sb.toString();

	}

	public static boolean deleteFile(String sPath) {

		boolean flag = false;

		File file = new File(sPath);

		if (file.isFile() && file.exists()) {

			file.delete();

			flag = true;

		}

		return flag;

	}
}