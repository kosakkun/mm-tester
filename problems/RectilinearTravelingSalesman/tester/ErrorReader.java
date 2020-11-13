import java.io.InputStream;

class ErrorReader extends Thread
{
    private static final int BUF_SIZE = 65536;
    final InputStream error;
    final StringBuilder sb;

    public ErrorReader (InputStream is) {
        error = is;
        sb = new StringBuilder();
    }

    @Override
    public void run () {
        try {
            byte[] buf = new byte[BUF_SIZE];
            int read;
            while ((read = error.read(buf)) > 0) {
                String s = new String(buf, 0, read);
                System.err.print(s);
                System.err.flush();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
