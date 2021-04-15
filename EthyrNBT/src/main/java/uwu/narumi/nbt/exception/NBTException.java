package uwu.narumi.nbt.exception;

public class NBTException extends RuntimeException {

  public NBTException() {
    super();
  }

  public NBTException(String message) {
    super(message);
  }

  public NBTException(String message, Throwable cause) {
    super(message, cause);
  }

  public NBTException(Throwable cause) {
    super(cause);
  }

  protected NBTException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
