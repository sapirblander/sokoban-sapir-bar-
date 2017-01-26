package model.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MyXMLLevelLoader implements LevelLoader {
	public Level loadLevel(InputStream in) {
		Level newLevel = new Level();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			doc = dBuilder.parse(in);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String s = doc.getDocumentElement().getAttribute("Height");
		int rows = Integer.valueOf(s);
		int columns = Integer.valueOf(doc.getDocumentElement().getAttribute(
				"Width"));
		NodeList nl = doc.getElementsByTagName("row");
		Storage[][] sokoban = new Storage[rows][columns];
		boolean[][] targets = new boolean[rows][columns];
		Position playerPosition = null;
		for (int i = 0; i < nl.getLength(); i++) {
			String row = nl.item(i).getTextContent();
			for (int j = 0; j < row.length(); j++) {
				char myChar = row.charAt(j);
				switch (myChar) {
				case '#':
					sokoban[i][j] = new Wall();
					break;
				case '@':
					sokoban[i][j] = new Box();
					break;
				case 'A':
					playerPosition = new Position(i, j);
					sokoban[i][j] = new Player();
					break;
				case 'o':
					targets[i][j] = true;
					sokoban[i][j] = new Target();
					break;
				default:
					sokoban[i][j] = new Space();
					break;
				}
			}
		}
		newLevel.setPlayerPosition(playerPosition);
		newLevel.setSokoban(sokoban);
		newLevel.setTargets(targets);
		return newLevel;
	}

	@Override
	public void saveLevel(OutputStream out, Level level) {
		Storage[][] sokoban = level.getSokoban();
		String start = "<Level Height = \"" + sokoban.length + "\" Width = \""
				+ sokoban[0].length + "\">\n";
		try {
			out.write(start.getBytes());
			for (int i = 0; i < sokoban.length; i++) {
				out.write("<row>".getBytes());
				for (int j = 0; j < sokoban[0].length; j++) {
					switch (sokoban[i][j].getStorageKind()) {
					case "Wall":
						out.write('#');
						break;
					case "Box":
						out.write('@');
						break;
					case "Player":
						out.write('A');
						break;
					case "Target":
						out.write('o');
						break;
					default:
						out.write(' ');
						break;
					}
				}
				out.write("</row>\n".getBytes());
			}
			out.write("</Level>".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
