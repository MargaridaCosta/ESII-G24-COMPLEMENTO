
/**
 * <h1>Class that contains all Enumerators</h1>
 * @author Miguel
 *
 */
public class Enums {

	/**
	 * Enumerated Type that represents a set of regions
	 * @author Miguel
	 *
	 */
	public static enum Regions {
		Alentejo, Algarve, Centro, Lisboa, Norte;

		/**
		 *<p>Function that outputs a string with the region's stringified value</p>
		 *@return String containing the region's name
		 */
		public String toString() {
			switch (this) {
			case Alentejo:
				return "Alentejo";
			case Algarve:
				return "Algarve";
			case Centro:
				return "Centro";
			case Lisboa:
				return "Lisboa";
			case Norte:
				return "Norte";
			}
			;
			return null;
		}
		
		/**
		 * <p>Function that checks if string belongs to Enum values</p>
		 * @param string - String that we want to test against the Enum values
		 * @return True if string belongs to Enum values. False if not
		 */
		public static boolean isRegions(String string) {
			switch (string) {
			case "Alentejo":
				return true;
			case "Algarve":
				return true;
			case "Centro":
				return true;
			case "Lisboa":
				return true;
			case "Norte":
				return true;
			}
			;
			return false;
		}
	}

	/**
	 * <p>Enumerated Type that represents a set of relational operators</p>
	 * @author Miguel
	 *
	 */
	public static enum RelationalOperators {
		GT, LT, GTE, LTE, NE, EQ;

		public String toString() {
			switch (this) {
			case GT:
				return "GT";
			case LT:
				return "LT";
			case GTE:
				return "GTE";
			case LTE:
				return "LTE";
			case NE:
				return "NE";
			case EQ:
				return "EQ";
			}
			;
			return null;
		}

		/**
		 * <p>Function that checks if string belongs to Enum values</p>
		 * @param string - String that we want to test against the Enum values
		 * @return True if string belongs to Enum values. False if not
		 */
		public static boolean isRelationalOperator(String string) {
			switch (string) {
			case "GT":
				return true;
			case "LT":
				return true;
			case "GTE":
				return true;
			case "LTE":
				return true;
			case "NE":
				return true;
			case "EQ":
				return true;
			}
			;
			return false;
		}
	}

	/**
	 * <p>Enumerated Type that represents a set of attributes</p>
	 * @author Miguel
	 *
	 */
	public static enum Attributes {
		Internamentos("Internamentos"), Infecoes("Infecoes"), Testes("Testes");

		private final String text;

		/**
		 * <p>Constructor that creates an instance of the Enumerated Type</p>
		 * @param text - String that defines Enumerated String value
		 */
		Attributes(final String text) {
			this.text = text;
		}

		/**
		 * <p>Method that outputs Enum Type String value</p>
		 *@return Enum Type String value
		 */
		public String toString() {
			return text;
		}
		
		/**
		 * <p>Function that checks if string belongs to Enum values</p>
		 * @param string - String that we want to test against the Enum values
		 * @return True if string belongs to Enum values. False if not
		 */
		public static boolean isAttributes(String string) {
			switch (string) {
			case "Internamentos":
				return true;
			case "Infecoes":
				return true;
			case "Testes":
				return true;
			}
			;
			return false;
		}

		/**
		 * Method that receives the Enumerated Type String value and returns the corresponding Enumerated 
		 * @param string Enumerated Type String value
		 * @return Corresponding Enumerated 
		 */
		public static Attributes getAttributes(final String string) {
	        switch (string) {
	            case "Testes": {
	                return Attributes.Testes;
	            }
	            case "Internamentos": {
	                return Attributes.Internamentos;
	            }
	            case "Infecoes": {
	                return Attributes.Infecoes;
	            }
	            default:
	                break;
	        }
	        return null;
	    }
	}

}
