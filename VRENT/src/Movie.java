
/*
 * Writer:
 * Version: 0.1
 * Time: 20/10/2015
 * 
 * */

/*
 * Writer:
 * Version: 0.2
 * Time: 21/10/2015
 * 
 * */

public class Movie {
	
	private String title;
	private int year;
	private String director;
	private String rating;
	private String genre;
	
	public Movie(String t, int yr, String dir, String r, String g) {
		this.title = t;
		this.year = yr;
		this.director = dir;
		this.rating = r;
		this.genre = g;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getYear() {
		return year;
	}
	
	public String getDirector() {
		return director;
	}
	
	public String getRating() {
		return rating;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setTitle(String t) {
		this.title = t;
	}
	
	public void setYear(int yr) {
		this.year = yr;
	}
	
	public void setDirector(String dir) {
		this.director = dir;
	}
	
	public void setRating(String r) {
		this.rating = r;
	}
	
	public void setGenre(String g) {
		this.genre = g;
	}

}
