public class Result<V> {
  public final V value; 
  public final String err; 

  public Result(String err) {
    this.value = null;
    this.err = err;
  }

  public Result(V value) {
    this.value = value;
    this.err = null;
  }
}
