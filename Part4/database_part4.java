/*
Sri Aditya Panda (UIN: 223003437)
Arjun Jayaraj Moothedath (UIN: 722008073)
Dakshina Ilangovan (UIN: 622009678)
Rajashree Rao Polsani (UIN: 223001584)
*/

import java.sql.*;
import java.util.*;

// ----------------------------------------------------------------------------------------------------------------------------------------------------
// class for connecting to database

class DBConnection {
	public static Connection getDBConnection() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://database2.cs.tamu.edu/";
		String dbName = "rpolsani";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "rpolsani";
		String password = "*****";
		Class.forName(driver).newInstance();
		System.out.println("Successfully connected to the database");
		return DriverManager.getConnection(url + dbName, userName, password);
	}

	public static void closeDBConnection(Connection conn) throws SQLException {
		conn.close();
	}
}

//------------------------------------------------------------------------------------------------------------------------------------------------------
// Utility class to print the output in a table format
class DBTablePrinter {

   private static final int DEFAULT_MAX_ROWS = 10;
   private static final int DEFAULT_MAX_TEXT_COL_WIDTH = 150;
   public static final int CATEGORY_STRING = 1;
   public static final int CATEGORY_INTEGER = 2;
   public static final int CATEGORY_DOUBLE = 3;
   public static final int CATEGORY_DATETIME = 4;
   public static final int CATEGORY_BOOLEAN = 5;
   public static final int CATEGORY_OTHER = 0;

   /**
    * Represents a DBConnection table column.
    */
   private static class Column {

       /**
        * Column label.
        */
       private String label;
       private int type;
       private String typeName;
       /**
        * Width of the column that will be adjusted according to column label
        * and values to be printed.
        */
       private int width = 0;

       /**
        * Column values from each row of a <code>ResultSet</code>.
        */
       private List<String> values = new ArrayList<String>();

       /**
        * Flag for text justification using <code>String.format</code>.
        * Empty string (<code>""</code>) to justify right,
        * dash (<code>-</code>) to justify left.
        *
        * @see #justifyLeft()
        */
       private String justifyFlag = "";

       /**
        * Column type category. The columns will be categorised according
        * to their column types and specific needs to print them correctly.
        */
       private int typeCategory = 0;

       /**
        * Constructs a new <code>Column</code> with a column label,
        * generic SQL type and type name 
        */
       public Column (String label, int type, String typeName) {
           this.label = label;
           this.type = type;
           this.typeName = typeName;
       }

       /**
        * Returns the column label
        *
        * @return Column label
        */
       public String getLabel() {
           return label;
       }

       /**
        * Returns the generic SQL type of the column
        *
        * @return Generic SQL type
        */
       public int getType() {
           return type;
       }

       /**
        * Returns the generic SQL type name of the column
        *
        * @return Generic SQL type name
        */
       public String getTypeName() {
           return typeName;
       }

       /**
        * Returns the width of the column
        *
        * @return Column width
        */
       public int getWidth() {
           return width;
       }

       /**
        * Sets the width of the column to <code>width</code>
        *
        * @param width Width of the column
        */
       public void setWidth(int width) {
           this.width = width;
       }

       /**
        * Adds a <code>String</code> representation (<code>value</code>)
        * of a value to this column object's {@link #values} list.
        * These values will come from each row 
        */
       public void addValue(String value) {
           values.add(value);
       }

       /**
        * Returns the column value at row index <code>i</code>.
        * Note that the index starts at 0 so that <code>getValue(0)</code>
        * will get the value for this column from the first row
        * of a relation
        *
        * @param i The index of the column value to get
        * @return The String representation of the value
        */
       public String getValue(int i) {
           return values.get(i);
       }

       /**
        * Returns the value of the {@link #justifyFlag}. The column
        * values will be printed using <code>String.format</code> and
        * this flag will be used to right or left justify the text.
        *
        * @return The {@link #justifyFlag} of this column
        * @see #justifyLeft()
        */
       public String getJustifyFlag() {
           return justifyFlag;
       }

       /**
        * Sets {@link #justifyFlag} to <code>"-"</code> so that
        * the column value will be left justified when printed with
        * <code>String.format</code>. Typically numbers will be right
        * justified and text will be left justified.
        */
       public void justifyLeft() {
           this.justifyFlag = "-";
       }

       /**
        * Returns the generic SQL type category of the column
        *
        * @return The {@link #typeCategory} of the column
        */
       public int getTypeCategory() {
           return typeCategory;
       }

       /**
        * Sets the {@link #typeCategory} of the column
        *
        * @param typeCategory The type category
        */
       public void setTypeCategory(int typeCategory) {
           this.typeCategory = typeCategory;
       }
   }

   /**
    * Overloaded method that prints rows from table <code>tableName</code>
    * to standard out using the given DBConnection connection
    */
   public static void printTable(Connection conn, String tableName){
       printTable(conn, tableName, DEFAULT_MAX_ROWS, DEFAULT_MAX_TEXT_COL_WIDTH);
   }

   /**
    * Overloaded method that prints rows from table <code>tableName</code>
    * to standard out using the given DBConnection connection
    * <code>conn</code>. Total number of rows will be limited to
    * <code>maxRows</code> and
    */
   public static void printTable(Connection conn, String tableName, int maxRows) {
       printTable(conn, tableName, maxRows, DEFAULT_MAX_TEXT_COL_WIDTH);
   }

   /**
    * Overloaded method that prints rows from table <code>tableName</code>
    * to standard out using the given DBConnection connection
    */
   public static void printTable(Connection conn, String tableName, int maxRows, int maxStringColWidth) {
       if (conn == null) {
           System.err.println("DBTablePrinter Error: No connection to DBConnection (Connection is null)!");
           return;
       }
       if (tableName == null) {
           System.err.println("DBTablePrinter Error: No table name (tableName is null)!");
           return;
       }
       if (tableName.length() == 0) {
           System.err.println("DBTablePrinter Error: Empty table name!");
           return;
       }
       if (maxRows < 1) {
           System.err.println("DBTablePrinter Info: Invalid max. rows number. Using default!");
           maxRows = DEFAULT_MAX_ROWS;
       }

       Statement stmt = null;
       ResultSet rs = null;
       try {
           if (conn.isClosed()) {
               System.err.println("DBTablePrinter Error: Connection is closed!");
               return;
           }

           String sqlSelectAll = "SELECT * FROM " + tableName + " LIMIT " + maxRows;
           stmt = conn.createStatement();
           rs = stmt.executeQuery(sqlSelectAll);

           printResultSet(rs, maxStringColWidth);

       } catch (SQLException e) {
           System.err.println("SQL exception in DBTablePrinter. Message:");
           System.err.println(e.getMessage());
       } finally {
           try {
               if (stmt != null) {
                   stmt.close();
               }
               if (rs != null) {
                   rs.close();
               }
           } catch (SQLException ignore) {
               // ignore
           }
       }
   }

   /**
    * Overloaded method to print rows of a 
    * ResultSet</a> to standard out using {@link #DEFAULT_MAX_TEXT_COL_WIDTH}
    * to limit the width of text columns.
    */
   public static void printResultSet(ResultSet rs) {
       printResultSet(rs, DEFAULT_MAX_TEXT_COL_WIDTH);
   }

   /**
    * Overloaded method to print rows of a <a target="_blank"
    * ResultSet</a> to standard out using <code>maxStringColWidth</code>
    * to limit the width of text columns.
    */
   public static void printResultSet(ResultSet rs, int maxStringColWidth) {
       try {
           if (rs == null) {
               System.err.println("DBTablePrinter Error: Result set is null!");
               return;
           }
           if (rs.isClosed()) {
               System.err.println("DBTablePrinter Error: Result Set is closed!");
               return;
           }
           if (maxStringColWidth < 1) {
               System.err.println("DBTablePrinter Info: Invalid max. varchar column width. Using default!");
               maxStringColWidth = DEFAULT_MAX_TEXT_COL_WIDTH;
           }

           // Get the meta data object of this ResultSet.
           ResultSetMetaData rsmd;
           rsmd = rs.getMetaData();

           // Total number of columns in this ResultSet
           int columnCount = rsmd.getColumnCount();

           // List of Column objects to store each columns of the ResultSet
           // and the String representation of their values.
           List<Column> columns = new ArrayList<Column>(columnCount);

           // List of table names. Can be more than one if it is a joined
           // table query
           List<String> tableNames = new ArrayList<String>(columnCount);

           // Get the columns and their meta data.
           // NOTE: columnIndex for rsmd.getXXX methods STARTS AT 1 NOT 0
           for (int i = 1; i <= columnCount; i++) {
               Column c = new Column(rsmd.getColumnLabel(i),
                       rsmd.getColumnType(i), rsmd.getColumnTypeName(i));
               c.setWidth(c.getLabel().length());
               c.setTypeCategory(whichCategory(c.getType()));
               columns.add(c);

               if (!tableNames.contains(rsmd.getTableName(i))) {
                   tableNames.add(rsmd.getTableName(i));
               }
           }

           // Go through each row, get values of each column and adjust
           // column widths.
           int rowCount = 0;
           while (rs.next()) {

               // NOTE: columnIndex for rs.getXXX methods STARTS AT 1 NOT 0
               for (int i = 0; i < columnCount; i++) {
                   Column c = columns.get(i);
                   String value;
                   int category = c.getTypeCategory();

                   if (category == CATEGORY_OTHER) {

                       // Use generic SQL type name instead of the actual value
                       // for column types BLOB, BINARY etc.
                       value = "(" + c.getTypeName() + ")";

                   } else {
                       value = rs.getString(i+1) == null ? "NULL" : rs.getString(i+1);
                   }
                   switch (category) {
                       case CATEGORY_DOUBLE:

                           // For real numbers, format the string value to have 3 digits
                           // after the point. THIS IS TOTALLY ARBITRARY and can be
                           // improved to be CONFIGURABLE.
                           if (!value.equals("NULL")) {
                               Double dValue = rs.getDouble(i+1);
                               value = String.format("%.3f", dValue);
                           }
                           break;

                       case CATEGORY_STRING:

                           // Left justify the text columns
                           c.justifyLeft();

                           // and apply the width limit
                           if (value.length() > maxStringColWidth) {
                               value = value.substring(0, maxStringColWidth - 3) + "...";
                           }
                           break;
                   }

                   // Adjust the column width
                   c.setWidth(value.length() > c.getWidth() ? value.length() : c.getWidth());
                   c.addValue(value);
               } // END of for loop columnCount
               rowCount++;

           } // END of while (rs.next)

           /*
           At this point we have gone through meta data, get the
           columns and created all Column objects, iterated over the
           ResultSet rows, populated the column values and adjusted
           the column widths.
           We cannot start printing just yet because we have to prepare
           a row separator String.
            */

           // For the fun of it, I will use StringBuilder
           StringBuilder strToPrint = new StringBuilder();
           StringBuilder rowSeparator = new StringBuilder();

           /*
           Prepare column labels to print as well as the row separator.
           It should look something like this:
           +--------+------------+------------+-----------+  (row separator)
           | EMP_NO | BIRTH_DATE | FIRST_NAME | LAST_NAME |  (labels row)
           +--------+------------+------------+-----------+  (row separator)
            */

           // Iterate over columns
           for (Column c : columns) {
               int width = c.getWidth();

             // Center the column label
               String toPrint;
               String name = c.getLabel();
               int diff = width - name.length();

               if ((diff%2) == 1) {
                   // diff is not divisible by 2, add 1 to width (and diff)
                   // so that we can have equal padding to the left and right
                   // of the column label.
                   width++;
                   diff++;
                   c.setWidth(width);
               }

               int paddingSize = diff/2; 

               String padding = new String(new char[paddingSize]).replace("\0", " ");

               toPrint = "| " + padding + name + padding + " ";
             // END centering the column label

               strToPrint.append(toPrint);

               rowSeparator.append("+");
               rowSeparator.append(new String(new char[width + 2]).replace("\0", "-"));
           }

           String lineSeparator = System.getProperty("line.separator");

           // Is this really necessary ??
           lineSeparator = lineSeparator == null ? "\n" : lineSeparator;

           rowSeparator.append("+").append(lineSeparator);

           strToPrint.append("|").append(lineSeparator);
           strToPrint.insert(0, rowSeparator);
           strToPrint.append(rowSeparator);

           StringBuilder sj = new StringBuilder();
           for (String name : tableNames) {
               sj.append(name+",");
           }

           String info = "Printing " + rowCount;
           info += rowCount > 1 ? " rows from " : " row from ";
           info += tableNames.size() > 1 ? "tables " : "table ";
           info += sj.toString();

           System.out.println(info);

           // Print out the formatted column labels
           System.out.print(strToPrint.toString());

           String format;

           // Print out the rows
           for (int i = 0; i < rowCount; i++) {
               for (Column c : columns) {

                   // This should form a format string like: "%-60s"
                   format = String.format("| %%%s%ds ", c.getJustifyFlag(), c.getWidth());
                   System.out.print(
                           String.format(format, c.getValue(i))
                   );
               }

               System.out.println("|");
               System.out.print(rowSeparator);
           }

           System.out.println();

           /*
               Hopefully this should have printed something like this:
               +--------+------------+------------+-----------+--------+-------------+
               | EMP_NO | BIRTH_DATE | FIRST_NAME | LAST_NAME | GENDER |  HIRE_DATE  |
               +--------+------------+------------+-----------+--------+-------------+
               |  10001 | 1953-09-02 | Georgi     | Facello   | M      |  1986-06-26 |
               +--------+------------+------------+-----------+--------+-------------+
               |  10002 | 1964-06-02 | Bezalel    | Simmel    | F      |  1985-11-21 |
               +--------+------------+------------+-----------+--------+-------------+
            */

       } catch (SQLException e) {
           System.err.println("SQL exception in DBTablePrinter. Message:");
           System.err.println(e.getMessage());
       }
   }

   /**
    * Takes a generic SQL type and returns the category this type
    * belongs to. Types are categorized according to print formatting
    * needs:
    * <p>
    * Integers should not be truncated so column widths should
    * be adjusted without a column width limit. Text columns should be
    * left justified and can be truncated to a max. column width etc...</p>
    */
   private static int whichCategory(int type) {
       switch (type) {
           case Types.BIGINT:
           case Types.TINYINT:
           case Types.SMALLINT:
           case Types.INTEGER:
               return CATEGORY_INTEGER;

           case Types.REAL:
           case Types.DOUBLE:
           case Types.DECIMAL:
               return CATEGORY_DOUBLE;

           case Types.DATE:
           case Types.TIME:
           //case Types.TIME_WITH_TIMEZONE:
           case Types.TIMESTAMP:
           //case Types.TIMESTAMP_WITH_TIMEZONE:
               return CATEGORY_DATETIME;

           case Types.BOOLEAN:
               return CATEGORY_BOOLEAN;

           case Types.VARCHAR:
           case Types.NVARCHAR:
           case Types.LONGVARCHAR:
           case Types.LONGNVARCHAR:
           case Types.CHAR:
           case Types.NCHAR:
               return CATEGORY_STRING;

           default:
               return CATEGORY_OTHER;
       }
   }
}

// ----------------------------------------------------------------------------------------------------------------------------------------------------------
// delete query

class Main {

	//Mapping between country index(first letter) and country listing. Each country listing has a key and its country name
	static TreeMap<String, TreeMap<Integer, String>> country_listing;
	//We find minimum year and max year of world cup date we have to validate year input from user
	static int minYear;
	static int maxYear;

	/* 
	 * This function gets a year as input from user. It deletes all teams that have 0 points so far
	 * and those that have participated in less than average number of world_cups all teams have participated
	 * after the user specified year. The user is reported if no teams are eligible for deletion.
	 */
	public static void deletePoorPeformers() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		if (fillMinAndMaxYearInfo()) {
			Scanner in = new Scanner(System.in);
			int year;

			System.out
					.println("Enter the year beyond which you want participations to be considered. Please restrict between "
							+ minYear + " and " + maxYear);
			try {
				year = in.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid input. Returning.");
				return;
			}

			if (year < minYear || year > maxYear) {
				System.out.println("Please restrict between " + minYear
						+ " and " + maxYear);
				System.out.println("Invalid input. Returning.");
				return;
			}

			String query = "delete from team where Country_Code in (select Country_Code from (select Country_Code from team natural join team_participates_in_world_cup where Points=0 and Year>"
					+ year
					+ " group by Country_Name having count(*)< (select avg(c) from (select count(*) as c from team natural join team_participates_in_world_cup where Points=0 and Year>"
					+ year
					+ " group by Country_Name)participation_statistics))country_list)";

			Connection conn = DBConnection.getDBConnection();
			Statement stmt = (Statement) conn.createStatement();
			int numRows = stmt.executeUpdate(query);
			//No rows deleted
			if (numRows > 0) {
				System.out
						.println(numRows
								+ " Teams with points 0 that had below average participation after "
								+ year + " have been deleted.");
			} else {
				System.out.println("No teams found!");
			}

			DBConnection.closeDBConnection(conn);
		}
	}
	
	
/*
 * This function finds minimum and maximum year of world cup recorded in the database.
 */
	public static boolean fillMinAndMaxYearInfo() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Connection conn = DBConnection.getDBConnection();
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT min(Year) as minYear, max(Year) as maxYear FROM world_cup");
		if (rs.next()) {
			minYear = rs.getInt("minYear");
			maxYear = rs.getInt("maxYear");
			DBConnection.closeDBConnection(conn);
			return true;
		} else {
			DBConnection.closeDBConnection(conn);
			return false;
		}
	}

	/*
	 * This function fills in the country_listing dictionary that maintains alphabetical listing of country index.
	 * A country index is a combination of a key and country name.
	 * This datastructure is used to help user choose a country. 
	 */
	public static boolean fillCountryInfo() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		country_listing = new TreeMap<String, TreeMap<Integer, String>>();
		Connection conn = DBConnection.getDBConnection();
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT Country_Name FROM team");

		while (rs.next()) {

			TreeMap<Integer, String> countries = new TreeMap<Integer, String>();
			//get country name
			String country_name = rs.getString("Country_Name").toLowerCase()
					.trim();
			//get country first charecter
			String country_char_1 = country_name.substring(0, 1);
			if (country_listing.containsKey(country_char_1)) {
				//existing index
				countries = country_listing.get(country_char_1);

			}
			int nextindex = countries.size() + 1;
			countries.put(nextindex, country_name);
			country_listing.put(country_char_1, countries);

		}
		//index built
		if (country_listing.size() != 0) {
			DBConnection.closeDBConnection(conn);
			return true;
		} else {
			DBConnection.closeDBConnection(conn);
			return false;
		}
	}

	/*
	 * This function gets a country as input from user.
	 * It determines the elite squad as players who have participated in atleast 2 winning world cups of the chosen country.
	 */
	public static void GetEliteSquad() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		if (fillCountryInfo()) {
			Scanner in = new Scanner(System.in);
			String country_name = "";

			System.out
					.println("Choose the first alphabet of the desired country");
			//display alphabetical listing
			for (String char_1 : country_listing.keySet()) {
				System.out.println(char_1);
			}

			String option_index;
			try {
				option_index = in.next().toLowerCase();
			} catch (Exception e) {
				System.out.println("Invalid input. Returning.");
				return;
			}

			if (country_listing.containsKey(option_index)) {
				TreeMap<Integer, String> countries = country_listing
						.get(option_index);
				//display country index for alphabet chosen
				for (Map.Entry<Integer, String> entry : countries.entrySet()) {
					System.out.println(entry.getKey() + "." + entry.getValue());
				}

				System.out.println("Choose index number of desired country");
				int option;
				try {
					option = in.nextInt();
				} catch (Exception e) {
					System.out.println("Invalid input. Returning.");
					return;
				}
				if (countries.containsKey(option)) {
					System.out.println("Chosen country:"
							+ countries.get(option));
					country_name = countries.get(option);
				} else {
					System.out.println("Invalid input. Returning.");
					return;
				}
			} else {
				System.out.println("Invalid input. Returning.");
				return;
			}

			String query = "select Player_Name from world_cup_played_by_player natural join team where lower(Country_Name)='"
					+ country_name
					+ "' and Year in ( select Year from world_cup, team where lower(team.Country_Name)='"
					+ country_name
					+ "' and world_cup.Winner=team.Country_Code ) group by Player_Name having count(*)>=2;";

			Connection conn = DBConnection.getDBConnection();
			Statement stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ResultSet test = rs;
			//check if records exist
			if (test.next()) {
				DBTablePrinter.printResultSet(rs);
			} else {
				System.out
						.println("No suitable records found to find elite squad in "
								+ country_name);
			}
			DBConnection.closeDBConnection(conn);
		}
		country_listing = null;
	}

	/*
	 * This query is to find the the number of Matches country A won against country B. 
	 * User has to choose whether he wants the results for a particular year or whether he wants for a particular stadium
	 * Results show the number of matches country A won straightly, matches won via penalty, matches that resulted in draw.
	*/
	public static void GetMatchesAWonAgainstB() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		boolean f = fillCountryInfo();
		if (f == false)
		{
			System.out.println("There are no countries. Returning");
			return;
		}
		// Asking the user to choose 2 countries in which he is interested in
		
		// First the winning team
		System.out.println("Choose the winning team");
		
		Scanner in = new Scanner(System.in);
		String win_country = "";

		System.out.println("Choose the first alphabet of the desired winning country");
		//display alphabetical listing
		for (String char_1 : country_listing.keySet()) {
			System.out.println(char_1);
		}

		String option_index;
		try {
			option_index = in.next().toLowerCase();
		} catch (Exception e) {
			System.out.println("Invalid input. Returning.");
			return;
		}
		
		if (country_listing.containsKey(option_index)) {
			TreeMap<Integer, String> countries = country_listing
					.get(option_index);
			//display country index for alphabet chosen
			for (Map.Entry<Integer, String> entry : countries.entrySet()) {
				System.out.println(entry.getKey() + "." + entry.getValue());
			}

			System.out.println("Choose index number of desired country");
			int option;
			try {
				option = in.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid input. Returning.");
				return;
			}
			if (countries.containsKey(option)) {
				System.out.println("Chosen country:"
						+ countries.get(option));
				win_country = countries.get(option);
			} else {
				System.out.println("Invalid input. Returning.");
				return;
			}
		} else {
			System.out.println("Invalid input. Returning.");
			return;
		}
		
		// Second loosing team

		System.out.println("Choose the loosing team");
		
		String loose_country = "";

		System.out.println("Choose the first alphabet of the desired loosing country");
		//display alphabetical listing
		for (String char_1 : country_listing.keySet()) {
			System.out.println(char_1);
		}

		try {
			option_index = in.next().toLowerCase();
		} catch (Exception e) {
			System.out.println("Invalid input. Returning.");
			return;
		}
		
		if (country_listing.containsKey(option_index)) {
			TreeMap<Integer, String> countries = country_listing
					.get(option_index);
			//display country index for alphabet chosen
			for (Map.Entry<Integer, String> entry : countries.entrySet()) {
				System.out.println(entry.getKey() + "." + entry.getValue());
			}

			System.out.println("Choose index number of desired country");
			int option;
			try {
				option = in.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid input. Returning.");
				return;
			}
			if (countries.containsKey(option)) {
				System.out.println("Chosen country:"
						+ countries.get(option));
				loose_country = countries.get(option);
			} else {
				System.out.println("Invalid input. Returning.");
				return;
			}
		} else {
			System.out.println("Invalid input. Returning.");
			return;
		}		
		
		// checking whether winning and loosing teams are equal. If not returning.
		if(win_country.equals(loose_country))
		{
			System.out.println("Error : Winning and Loosing country are same. Returning.");
			return;
		}
		
		// Presenting filtering options to the user
		System.out.println("Choose one of the filtering parameter (Enter the number associated with it):");
		System.out.println("1. Filter by year");
		System.out.println("2. Filter by Stadium");
		
		int filter_option = 0;
		filter_option = in.nextInt();
		
		if (filter_option != 1 && filter_option!= 2)
		{
			System.out.println("Invalid option. Returning.");
			return;
		}
		
		switch(filter_option)
		{
			// filter by year
			case 1:
			{
				List<Integer> years = new ArrayList<Integer>();
				// showing only those years in which both the countries have participated
				String year_query = "select distinct(Date_Year) temp from matches "
						+ "where (Team_1 = (select Country_Code from team where Country_Name = '"+win_country+"') and "
						+ "Team_2 = (select Country_Code from team where Country_Name = '"+loose_country+"')) or "
						+ "(Team_1 = (select Country_Code from team where Country_Name ='"+loose_country+"') and "
						+ "Team_2 = (select Country_Code from team where Country_Name ='"+win_country+"'));";
				Connection conn = DBConnection.getDBConnection();
				Statement stmt = (Statement) conn.createStatement();
				ResultSet rs = stmt.executeQuery(year_query);
				int counter = 0;
				// Asking the user to choose the year option
				System.out.println("Choose one of the index corresponding to the year you are interested in");
				while (rs.next())
				{
					counter++;
					int year = rs.getInt("temp");
					years.add(year);
					System.out.println(counter+"."+year);
				}
				int option = in.nextInt();
				if (option<= 0 || option>counter)
				{
					System.out.println("Invalid option. Returning.");
					return;
				}
				int chosen_year = years.get(option-1);
				// Getting number of wins with and without penalties of winning against the loosing country
				String query = "select sum(case when Decision = 'WINNER' then 1 else 0 end) WinTotal, "
						+ "sum(case when Decision = 'PENALTY' then 1 else 0 end) PenaltyTotal "
						+ "from matches where (Winner = (select Country_Code from team where Country_Name ='"+win_country+"')) and "
						+ "(Team_1 = (select Country_Code from team where Country_Name ='"+loose_country+"') or "
						+ "Team_2 = (select Country_Code from team where Country_Name ='"+loose_country+"')) and Date_Year = "+chosen_year+";";
				ResultSet rs1 = stmt.executeQuery(query);
				rs1.next();
				System.out.println("Number of matches " + win_country + " won against "+loose_country+" without penalties : "+rs1.getInt("WinTotal"));
				System.out.println("Number of matches " + win_country + " won against "+loose_country+" with penalties: "+rs1.getInt("PenaltyTotal"));
				// Getting number of draw matches between winning and loosing team
				String query1 = "select sum(case when Decision = 'DRAW' then 1 else 0 end) DrawTotal "
						+ "from matches where (Team_1 = (select Country_Code from team where Country_Name ='"+win_country+"') "
						+ "and Team_2 = (select Country_Code from team where Country_Name ='"+loose_country+"')) "
						+ "or (Team_1 = (select Country_Code from team where Country_Name ='"+loose_country+"') and "
						+ "Team_2 = (select Country_Code from team where Country_Name ='"+win_country+"')) and Date_Year = "+chosen_year+";";
				ResultSet rs12 = stmt.executeQuery(query1);
				rs12.next();
				System.out.println("Number of matches " + win_country + " drew against "+loose_country+" : "+rs12.getInt("DrawTotal"));
				rs.close();
				rs1.close();
				stmt.close();
				DBConnection.closeDBConnection(conn);
				break;
			}
			// filter by stadium
			case 2:
			{
				// showing only those stadiums in which both the countries have participated
				List<String> stadiums = new ArrayList<String>();
				String stadium_query = "select distinct(Stadium) temp from matches "
						+ "where (Team_1 = (select Country_Code from team where Country_Name = '"+win_country+"') and "
						+ "Team_2 = (select Country_Code from team where Country_Name = '"+loose_country+"')) or "
						+ "(Team_1 = (select Country_Code from team where Country_Name ='"+loose_country+"') and "
						+ "Team_2 = (select Country_Code from team where Country_Name ='"+win_country+"'));";
				Connection conn = DBConnection.getDBConnection();
				Statement stmt = (Statement) conn.createStatement();
				ResultSet rs = stmt.executeQuery(stadium_query);
				int counter = 0;
				// Asking the user to choose the stadium option
				System.out.println("Choose one of the index corresponding to the stadium you are interested in");
				while (rs.next())
				{
					counter++;
					String stadium = rs.getString("temp");
					stadiums.add(stadium);
					System.out.println(counter+"."+stadium);
				}
				int option = in.nextInt();
				if (option<= 0 || option>counter)
				{
					System.out.println("Invalid option. Returning.");
					return;
				}
				String chosen_stadium = stadiums.get(option-1);
				// Getting number of wins with and without penalties of winning against the loosing country
				String query = "select sum(case when Decision = 'WINNER' then 1 else 0 end) WinTotal, "
						+ "sum(case when Decision = 'PENALTY' then 1 else 0 end) PenaltyTotal "
						+ "from matches where (Winner = (select Country_Code from team where Country_Name ='"+win_country+"')) "
						+ "and (Team_1 = (select Country_Code from team where Country_Name ='"+loose_country+"') or "
						+ "Team_2 = (select Country_Code from team where Country_Name ='"+loose_country+"')) and Stadium = '"+chosen_stadium+"';";
				ResultSet rs1 = stmt.executeQuery(query);
				rs1.next();
				System.out.println("Number of matches " + win_country + " won against "+loose_country+" without penalties : "+rs1.getInt("WinTotal"));
				System.out.println("Number of matches " + win_country + " won against "+loose_country+" with penalties: "+rs1.getInt("PenaltyTotal"));
				// Getting number of draw matches between winning and loosing team
				String query1 = "select sum(case when Decision = 'DRAW' then 1 else 0 end) DrawTotal "
						+ "from matches where (Team_1 = (select Country_Code from team where Country_Name ='"+win_country+"') and "
						+ "Team_2 = (select Country_Code from team where Country_Name ='"+loose_country+"')) or "
						+ "(Team_1 = (select Country_Code from team where Country_Name ='"+loose_country+"') and "
						+ "Team_2 = (select Country_Code from team where Country_Name ='"+win_country+"')) and Stadium = '"+chosen_stadium+"';";
				ResultSet rs12 = stmt.executeQuery(query1);
				rs12.next();
				System.out.println("Number of matches " + win_country + " drew against "+loose_country+" : "+rs12.getInt("DrawTotal"));
				rs.close();
				rs1.close();
				stmt.close();
				DBConnection.closeDBConnection(conn);
				break;
			}
			default :
			{
				System.out.println("Invalid filter option chosen. Returning.");
				break;
			}
		}
		country_listing = null;
		return;
	}
	
	public static void executeUpdate() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        Scanner reader = new Scanner(System.in);

        // Display sub options
        System.out.println("");
        System.out.println("");

        System.out.println("1. Previous ranking takes precedence");
        System.out.println("2. Teams with more participation in the World Cup takes precedence");
        System.out.println("3. Teams with more World Cup wins takes precedence");

        // Read user input
        int choice;
        System.out.print("Enter your tiebreaker option: ");
        choice = reader.nextInt();

        switch(choice)
        {
            case 1:
                executeUpdateQuery(1);
                break;

            case 2:
                executeUpdateQuery(2);
                break;

            case 3:
                executeUpdateQuery(3);
                break;
        }
    }

    public static void executeUpdateQuery(int tieBreakerChoice) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
    	Connection conn = DBConnection.getDBConnection();
        try {
            // This map is used to maintain a mapping from the number of goals scored
            // to the countries who have scored that many goals
            TreeMap <Integer, List<String>> goalCountryMap = new TreeMap<Integer, List<String>>();

            // This map is used to store a mapping from the ranking (updated) to the country
            TreeMap <Integer, String> rankMap = new TreeMap<Integer, String>();

            // Get the number of goals scored by each country in descending order
            String selQuery = "" +
                    "SELECT COUNT(*) AS Goals_Scored, Country_Code " +
                    "FROM goal_and_player_scores_goals " +
                    "GROUP BY Country_Code " +
                    "ORDER BY Goals_Scored DESC";

            // Form the statement and execute the select query
            Statement s = conn.createStatement();
            s.executeQuery(selQuery);

            // Get the result set
            ResultSet rs = s.getResultSet();
            ResultSetMetaData rsMeta = rs.getMetaData();
            int numberOfColumns = rsMeta.getColumnCount();

            // Number of rows retrieved
            int count = 0;

            while (rs.next())
            {
                // Get the table columns
                String countryCode = rs.getString("Country_Code");
                Integer goalsScored = rs.getInt("Goals_Scored");

                // Insert into the goalCountryMap
                if (!goalCountryMap.containsKey(goalsScored))
                {
                    List<String> countryList = new ArrayList<String>();
                    countryList.add(countryCode);
                    goalCountryMap.put(goalsScored, countryList);
                }
                else
                {
                    goalCountryMap.get(goalsScored).add(countryCode);
                }

                // Increment row count
                ++count;
            }
            rs.close();
            s.close();

            // Get countries which have not scored in the world cup yet into the map
            String getQuery = "SELECT Country_Code, Points, Ranking " +
                    "FROM team WHERE Country_Code NOT IN (SELECT Country_Code " +
                                                          "FROM goal_and_player_scores_goals " +
                                                          "GROUP BY Country_Code)";
            Statement s1 = conn.createStatement();
            s1.executeQuery(getQuery);

            // Get the result set
            ResultSet rs1 = s1.getResultSet();
            List<String> ccList = new ArrayList<String>();
            while(rs1.next())
            {
                // Get the table columns
                String countryCode = rs1.getString("Country_Code");
                ccList.add(countryCode);
            }
            goalCountryMap.put(0, ccList);
            s1.close();
            rs1.close();

            // Initialize ranking variable to 1
            int ranking = 1;
            for (Map.Entry<Integer, List<String>> entry : goalCountryMap.descendingMap().entrySet())
            {
                //System.out.println(entry.getKey() + " : " + entry.getValue());
                List<String> countryList = entry.getValue();
                if (countryList.size() == 1)
                {
                    rankMap.put(ranking, countryList.get(0));
                    ranking++;
                }
                else
                {
                    // In case there are multiple countries who have scored the same number
                    // of goals, then we use the tie breaker condition which is based on the user
                    // choice.
                    String selectQuery;
                    switch (tieBreakerChoice)
                    {
                        case 1:
                            // Use the previous ranking to establish ordering
                            StringBuilder cList = new StringBuilder();
                            cList.append("(");
                            for (int i = 0; i < countryList.size(); i++)
                            {
                                cList.append("'" +countryList.get(i)+ "'");
                                cList.append(",");
                            }
                            cList.deleteCharAt(cList.length() - 1);
                            cList.append(")");
                            selectQuery = "SELECT Country_Code " +
                                          "FROM team " +
                                          "WHERE Country_Code IN " +cList +
                                          "ORDER BY Ranking, Points DESC";

                            Statement st = conn.createStatement();
                            st.executeQuery(selectQuery);

                            // Get the result set
                            ResultSet rSet = st.getResultSet();
                            while (rSet.next())
                            {
                                String cCode = rSet.getString("Country_Code");
                                rankMap.put(ranking, cCode);
                                ranking++;
                            }
                            rSet.close();
                            st.close();
                            break;

                        case 2:
                            // Use the world cup participation to establish ordering
                            StringBuilder cList1 = new StringBuilder();
                            cList1.append("(");
                            for (int i = 0; i < countryList.size(); i++)
                            {
                                cList1.append("'" +countryList.get(i)+ "'");
                                cList1.append(",");
                            }
                            cList1.deleteCharAt(cList1.length() - 1);
                            cList1.append(")");

                            selectQuery = "SELECT COUNT(*) AS Times_Participated, Country_Code " +
                                    "FROM team_participates_in_world_cup " +
                                    "WHERE Country_Code IN " +cList1 +
                                    "GROUP BY Country_Code " +
                                    "ORDER BY Times_Participated";

                            Statement st1 = conn.createStatement();
                            st1.executeQuery(selectQuery);

                            // Get the result set
                            ResultSet rSet1 = st1.getResultSet();
                            while (rSet1.next())
                            {
                                String cCode = rSet1.getString("Country_Code");
                                rankMap.put(ranking, cCode);
                                ranking++;
                            }
                            rSet1.close();
                            st1.close();

                            break;

                        case 3:
                            // Use world cup match wins to establish ordering
                            StringBuilder cList2 = new StringBuilder();
                            cList2.append("(");
                            for (int i = 0; i < countryList.size(); i++)
                            {
                                cList2.append("'" +countryList.get(i)+ "'");
                                cList2.append(",");
                            }
                            cList2.deleteCharAt(cList2.length() - 1);
                            cList2.append(")");

                            selectQuery = "SELECT count(*) AS Matches_Won, Winner " +
                                    "FROM (SELECT * FROM matches WHERE Winner != 'NUL' AND Winner IN "+cList2+") DecidedMatches " +
                                    "GROUP BY Winner " +
                                    "ORDER BY Matches_Won DESC";

                            Statement st2 = conn.createStatement();
                            st2.executeQuery(selectQuery);

                            // Get the result set
                            ResultSet rSet2 = st2.getResultSet();

                            while (rSet2.next())
                            {
                                String cCode = rSet2.getString("Winner");
                                countryList.remove(cCode);
                                rankMap.put(ranking, cCode);
                                ranking++;
                            }

                            while (countryList.size() != 0)
                            {
                                // Put the rest of countries in the list into the Map
                                String cCode = countryList.get(0);
                                countryList.remove(cCode);
                                rankMap.put(ranking, cCode);
                                ranking++;
                            }
                            rSet2.close();
                            st2.close();

                            break;
                    }
                }
            }

            // Update the ranking of the teams
            for (Map.Entry<Integer, String> entry : rankMap.entrySet())
            {
                String updateQuery = "UPDATE team " +
                        "SET Ranking = " +entry.getKey()+
                        " WHERE Country_Code = '" +entry.getValue()+ "'";

                //System.out.println(updateQuery);
                Statement updateStatement = conn.createStatement();
                updateStatement.executeUpdate(updateQuery);
                updateStatement.close();
            }
            System.out.println("Successfully updated");

        }
        catch(SQLException e)
        {
            for (StackTraceElement i : Thread.currentThread().getStackTrace())
                System.out.println(i);
        }
        DBConnection.closeDBConnection(conn);
        return;
    }
    
   /* This functionality allows user to set up information for a new world cup. The user can add any additional countries that are not in list of 
    * countries maintained by team table. The user is then prompted to add new world cup year along with host country, winner and runner up for that year. 
    * Note that winner and runner up should belong to list of countries maintained in team table.
    */
    // INSERT QUERY
    public static void insert() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{ 
    	
		// opening database connection
		Connection conn = DBConnection.getDBConnection();
		Statement stmt = (Statement) conn.createStatement();
		
		boolean f = fillCountryInfo();
		if (f == false)
		{
			System.out.println("There are no countries. Returning");
			return;
		}
		
		// Giving user some choices for host country (host country may or may not participate in the world cup so we need not check if it is in team table)
		Scanner in = new Scanner(System.in);
		String host_country = "";

		System.out.println("Choose the first alphabet of host country");
		//display alphabetical listing
		for (String char_1 : country_listing.keySet()) {
			System.out.println(char_1);
		}

		String option_index;
		try {
			option_index = in.next().toLowerCase();
		} catch (Exception e) {
			System.out.println("Invalid input. Returning.");
			return;
		}
		
		if (country_listing.containsKey(option_index)) {
			TreeMap<Integer, String> countries = country_listing
					.get(option_index);
			//display country index for alphabet chosen
			for (Map.Entry<Integer, String> entry : countries.entrySet()) {
				System.out.println(entry.getKey() + "." + entry.getValue());
			}

			System.out.println("Choose index number of desired country else type -1 and enter a new name");
			int option;
			try {
				option = in.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid input. Returning.");
				return;
			}
			if (countries.containsKey(option)) {
				System.out.println("Chosen country:"
						+ countries.get(option));
				host_country = countries.get(option);
			} else if (option == -1)
			{
				host_country = in.next();
			}
			else {
				System.out.println("Invalid input. Returning.");
				return;
			}
		} else {
			System.out.println("Invalid input. Returning.");
			return;
		}

		// asking the user to enter runner up country
		// Giving user some choices for runner up country 
		String runner_country = "";

		System.out.println("Choose the first alphabet of the runner up country");
		//display alphabetical listing
		for (String char_1 : country_listing.keySet()) {
			System.out.println(char_1);
		}

		try {
			option_index = in.next().toLowerCase();
		} catch (Exception e) {
			System.out.println("Invalid input. Returning.");
			return;
		}
		
		if (country_listing.containsKey(option_index)) {
			TreeMap<Integer, String> countries = country_listing
					.get(option_index);
			//display country index for alphabet chosen
			for (Map.Entry<Integer, String> entry : countries.entrySet()) {
				System.out.println(entry.getKey() + "." + entry.getValue());
			}

			System.out.println("Choose index number of desired country else type -1 and enter a new name");
			int option;
			try {
				option = in.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid input. Returning.");
				return;
			}
			if (countries.containsKey(option)) {
				System.out.println("Chosen country:"
						+ countries.get(option));
				runner_country = countries.get(option);
			} else if (option == -1)
			{
				// As the runner country entered by user is not in the team table taking information so as to populate team table
				runner_country = in.next();
				System.out.println("Enter the association");
				String assoc = in.next();
				System.out.println("Enter the country code");
				String country_code = in.next();
				if (team_exists(country_code))
				{
					System.out.println("Country code already exists. returning");
					return;
				}
				System.out.println("Enter ranking");
				int rank = in.nextInt();
				System.out.println("Enter points");
				int points = in.nextInt();
				String insert_query = "insert into team (Country_Code, Country_Name,Association, Points, Ranking) values ('" + country_code + "','" + runner_country + "','" + assoc + "',"+points+","+rank+");";
				stmt.executeUpdate(insert_query);
				System.out.println("Insert of runner up country successfully executed");
			}
			else {
				System.out.println("Invalid input. Returning.");
				return;
			}
		} else {
			System.out.println("Invalid input. Returning.");
			return;
		}
		
		// asking the user to enter winner country
		// Giving user some choices for winner country 
		String winner_country = "";

		System.out.println("Choose the first alphabet of the winning country");
		//display alphabetical listing
		for (String char_1 : country_listing.keySet()) {
			System.out.println(char_1);
		}

		try {
			option_index = in.next().toLowerCase();
		} catch (Exception e) {
			System.out.println("Invalid input. Returning.");
			return;
		}
		
		if (country_listing.containsKey(option_index)) {
			TreeMap<Integer, String> countries = country_listing
					.get(option_index);
			//display country index for alphabet chosen
			for (Map.Entry<Integer, String> entry : countries.entrySet()) {
				System.out.println(entry.getKey() + "." + entry.getValue());
			}

			System.out.println("Choose index number of desired country else type -1 and enter a new name");
			int option;
			try {
				option = in.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid input. Returning.");
				return;
			}
			if (countries.containsKey(option)) {
				System.out.println("Chosen country:"
						+ countries.get(option));
				winner_country = countries.get(option);
			} else if (option == -1)
			{
				// As the winner country entered by user is not in the team table taking information so as to populate team table
				winner_country = in.next();
				System.out.println("Enter the association");
				String assoc = in.next();
				System.out.println("Enter the country code");
				String country_code = in.next();
				if (team_exists(country_code))
				{
					System.out.println("Country code already exists. returning");
					return;
				}
				System.out.println("Enter ranking");
				int rank = in.nextInt();
				System.out.println("Enter points");
				int points = in.nextInt();
				String insert_query = "insert into team (Country_Code, Country_Name,Association, Points, Ranking) values ('" + country_code + "','" + winner_country + "','" + assoc + "',"+points+","+rank+");";
				ResultSet rs = stmt.executeQuery(insert_query);
				System.out.println("Insert of winning country successfully executed");
				rs.close();
			}
			else {
				System.out.println("Invalid input. Returning.");
				return;
			}
		} else {
			System.out.println("Invalid input. Returning.");
			return;
		}
		
		// checking if runner and winner are equal , if so return
		if(winner_country.equals(runner_country))
		{
			System.out.println("Runner up and winner countries are equal. Returning.");
			return;
		}
		
		System.out.println("Enter the year for which worldcup details are to be entered");
    	int year = in.nextInt();
    	ResultSet rs = stmt.executeQuery("select Country_Code from team where Country_Name = '" + winner_country +"';");
    	rs.next();
    	String winner_code = rs.getString("Country_Code");
    	rs.close();
    	rs = stmt.executeQuery("select Country_Code from team where Country_Name = '" + runner_country +"';");
    	rs.next();
    	String runner_code = rs.getString("Country_Code");
    	rs.close();
		// Inserting the entries into world cup table
		String insert_query = "insert into world_cup  values ("+year+",'" + host_country + "','"+winner_code+"','"+runner_code+"');";
		stmt.executeUpdate(insert_query);
		System.out.println("Insert into world cup successfully executed");
		stmt.close();
		DBConnection.closeDBConnection(conn);
		country_listing = null;
	}
	
	public static boolean team_exists(String Country_code) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Connection conn = DBConnection.getDBConnection();
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery("select Country_Code from team where Country_Code = '" + Country_code +"';");
		if(rs.next()) {
			rs.close();
			stmt.close();
			DBConnection.closeDBConnection(conn);
			return true;
		}
		return false;
	}
}

// -----------------------------------------------------------------------------------------------------------------------------------
// Main class
public class database_part4
{
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		while(true) {
			//Printing out all the options
			System.out.println("Football Database");
			System.out.println("Please choose an option:");
			System.out.println("1. Insert : This functionality allows user to set up information for a new world cup. The user can add any additional "
					+ "countries that are not in list of countries maintained by team table. The user is then prompted to add new world cup year along "
					+ "with host country, winner and runner up for that year. Note that winner and runner up should belong to list of countries maintained "
					+ "in team table.");
			System.out.println("2. Update : Update ranking of countries based on number of goals scored till now. In case of a tie the user has "
					+ "to choose one of the tie-breakers 1. previous ranking 2. Number of world cup participations (the more teh better rank) "
					+ "3. Number of world cup wins");
			System.out.println("3. Delete : This function gets a year as input from user. It deletes all teams that have 0 points so far and "
					+ "those that have participated in less than average number of world_cups all teams have participated after the user specified year. "
					+ "The user is reported if no teams are eligible for deletion.");
			System.out.println("4. Select1 : This query is to find the the number of Matches country A won against country B. User enters the both the "
					+ "country names. User has to choose whether he wants the results for a particular year or whether he wants for a particular stadium "
					+ "Results show the number of matches team A won straightly, matches won via penalty, matches that resulted in draw ");
			System.out.println("5. Select2 : This function gets a country as input from user. It determines the elite squad as players who have "
					+ "participated in atleast 2 winning world cups of the chosen country.");
			System.out.println("6. Quit");
			
			//User input of option
			Scanner in = new Scanner(System.in);
			int input_val = in.nextInt();			
			
			if(input_val == 6)
			{
				System.out.println("Quitting");
				break;
			}
			try {
					switch(input_val) {
					case 1: 
					{
						System.out.println("Option 1 selected");
						Main.insert();
						break;
					}
					case 2: 
					{
						System.out.println("Option 2 selected");
						Main.executeUpdate();
						break;
					}		
					case 3: 
					{
						System.out.println("Option 3 selected");
						Main.deletePoorPeformers();
						break;
					}
					case 4: 
					{
						System.out.println("Option 4 selected");
						Main.GetMatchesAWonAgainstB();
						break;
					}
					case 5: 
					{
						System.out.println("Option 5 selected");
						Main.GetEliteSquad();
						break;
					}
					default: System.out.println("Wrong option selected");
							break;
					}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
}



