public class QuantumUtils {

	private ScriptEngineManager mgr = new ScriptEngineManager();
	private ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");

	public static Double solveEquation(String equation) {
		try {
			return ((Double) jsEngine.eval(equation));
		} catch (ScriptException) {
			return null;
		}
	}
}