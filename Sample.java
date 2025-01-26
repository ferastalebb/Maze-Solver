public class Sample {

    public static void main (String[] args) {
	try {
	    SoundPlayer player = new SoundPlayer();
	    player.play("spring.wav");

	    PictureViewer viewer = new PictureViewer();
	    viewer.show("flower.jpg");
	    
	    ShowHTML browser = new ShowHTML();
	    browser.show("matrices.html");
	}
	catch (MultimediaException e) {
	    System.out.println(e.getMessage());
	}
    }
}
