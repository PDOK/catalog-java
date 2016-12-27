package nl.pdok.catalog.tiling;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TilingLayer {
	private static final int DEFAULT_THREAD_COUNT = 1;
	
	private String layername;
	private String[] gridsetid;
	private String[] formats;

	private Zoom pretile;
	private Zoom reseed;
	private Zoom truncate;
	private int threadcount=DEFAULT_THREAD_COUNT;

	private String[] boundingbox;

	public TilingLayer() {
		super();
	}

	public String getLayername() {
		return layername;
	}

	public void setLayername(String layername) {
		this.layername = layername;
	}
	
	public String[] getGridsetid() {
		return gridsetid;
	}

	public void setGridsetid(String[] gridsetid) {
		this.gridsetid = gridsetid;
	}

	public String[] getFormats() {
		return formats;
	}

	public void setFormats(String[] formats) {
		this.formats = formats;
	}

	public Zoom getPretile() {
		return pretile;
	}

	public void setPretile(Zoom pretile) {
		this.pretile = pretile;
	}

	public Zoom getReseed() {return reseed;}

	public void setReseed(Zoom reseed) {this.reseed = reseed;}

	public Zoom getTruncate() {return truncate;}

	public void setTruncate(Zoom truncate) {this.truncate = truncate;}

	public String[] getBoundingbox() {
		return boundingbox;
	}

	public void setBoundingbox(String[] boundingbox) {
		this.boundingbox = boundingbox;
	}
	
	public int getThreadcount() {
		return threadcount;
	}

	public void setThreadcount(int threadcount) {
		this.threadcount = threadcount;
	}

	public class Zoom {
		
		private int start;
		private int stop;
		
		public int getStart() {
			return start;
		}
		
		public void setStart(int start) {
			this.start = start;
		}
		
		public int getStop() {
			return stop;
		}
		
		public void setStop(int stop) {
			this.stop = stop;
		}		
	}
}
