package theatricalplays;

public class Play {

  public String name;
  public PlayType type;

  public Play(String name, PlayType type) {
    this.name = name;
    this.type = type;
  }
  public enum PlayType {
    TRAGEDY,
    COMEDY
  }
}
