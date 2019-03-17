package sk.itsovy.sova7;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Database implements carmethods{
	
	
	
	@Override
	public void addCar(Car car) {
		Connection conn = getConnection(ConnectionType.INSERT);
		try {
			String sql = "INSERT INTO cars (brand, color, fuel, spz, price) VALUES(?,?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, car.getBrand());
			statement.setString(2, car.getColor());
			statement.setString(3, car.getFuel() + "");
			
			statement.setString(4, car.getSpz());
			statement.setInt(5, car.getPrice());
			int result = statement.executeUpdate();
			System.out.println(result);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Car> getCarsByPrice(int maxPrice) {
		LinkedList<Car> list = new LinkedList<Car>();
		Connection conn = getConnection(ConnectionType.SELECT);
		try {
			String sql = "SELECT * from cars where price <= ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, maxPrice);
		
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				list.add(new Car(result.getString(2),result.getString(3),result.getString(5),result.getString(4).charAt(0),result.getInt(6)));
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}

	@Override
	public List<Car> getCarsByBrand(String brand) {
		LinkedList<Car> list = new LinkedList<Car>();
		Connection conn = getConnection(ConnectionType.SELECT);
		try {
			String sql = "SELECT * from cars where brand like ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, brand + "%");
		
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				list.add(new Car(result.getString(2),result.getString(3),result.getString(5),result.getString(4).charAt(0),result.getInt(6)));
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Car> getCarsByFuel(char fuel) {
		LinkedList<Car> list = new LinkedList<Car>();
		Connection conn = getConnection(ConnectionType.SELECT);
		try {
			String sql = "SELECT * from cars where fuel like ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, fuel+"");
		
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				list.add(new Car(result.getString(2),result.getString(3),result.getString(5),result.getString(4).charAt(0),result.getInt(6)));
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Car> getCarsByRegion(String spz) {
		LinkedList<Car> list = new LinkedList<Car>();
		Connection conn = getConnection(ConnectionType.SELECT);
		try {
			String sql = "SELECT * from cars where spz like ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, spz + "%");
		
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				list.add(new Car(result.getString(2),result.getString(3),result.getString(5),result.getString(4).charAt(0),result.getInt(6)));
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void changeSPZ(String oldSPZ, String newSPZ) {
		Connection conn = getConnection(ConnectionType.UPDATE);
		try {
			String sql = "Update cars set spz = ? where spz like ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, newSPZ);
			statement.setString(2, oldSPZ);
			int result = statement.executeUpdate();
			System.out.println(result);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	public List<Car> selectAllCars(){
		Connection conn = getConnection(ConnectionType.SELECT);
		LinkedList<Car> allCars = new LinkedList<Car>();
		String sql = "Select * from cars";
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				allCars.add(new Car(result.getString(2),result.getString(3),result.getString(5),result.getString(4).charAt(0),result.getInt(6)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return allCars;
	}
	@Override
	public void generateXML() {
		List<Car> cars = selectAllCars();
		//cars.stream().forEach(item -> System.out.println(item.getBrand()));
		
		try {
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		    Document doc = docBuilder.newDocument();
		    Element rootElement = doc.createElement("Cars");
		    for(Car c:cars) {
		    	Element Car = doc.createElement("Car");
		    	Element brand = doc.createElement("brand");
		    	brand.appendChild(doc.createTextNode(c.getBrand()));
		    	Car.appendChild(brand);
		    	Element color = doc.createElement("color");
		    	color.appendChild(doc.createTextNode(c.getColor()));
		    	Car.appendChild(color);
		    	Element SPZ = doc.createElement("SPZ");
		    	SPZ.appendChild(doc.createTextNode(c.getSpz()));
		    	Car.appendChild(SPZ);
		    	Element fuel = doc.createElement("fuel");
		    	fuel.appendChild(doc.createTextNode(c.getFuel()+"")); 
		    	Car.appendChild(fuel);
		    	Element price = doc.createElement("price");
		    	price.appendChild(doc.createTextNode(Integer.toString(c.getPrice()))); 
		    	Car.appendChild(price);
		    	rootElement.appendChild(Car);
		    }
		    doc.appendChild(rootElement);
		    TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    Transformer transformer = transformerFactory.newTransformer();
		    DOMSource source = new DOMSource(doc);
		    StreamResult result = new StreamResult(new File("cars.xml"));
		    transformer.transform(source, result);
		    System.out.println("File saved!");
		  } catch (ParserConfigurationException pce) {
		    pce.printStackTrace();
		  } catch (TransformerException tfe) {
		    tfe.printStackTrace();
		  }
		
	}
	public Connection getConnection(ConnectionType type) {
		String[][] connLogin = {{"sdfaf15","ins3r1"},{"asdff135","upd2t3"},{"bidafzobi","d31313"}};
		final String host = "jdbc:mysql://localhost/sova7";
		String[] connInfo = new String[2];
		
		switch(type) {
			case INSERT:
				connInfo = connLogin[0];
				break;
			case UPDATE:
				connInfo = connLogin[1];
				break;
			default:
				connInfo = connLogin[2];
				break;
		}
		
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(host, connInfo[0], connInfo[1]);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  return conn;
	}

}
