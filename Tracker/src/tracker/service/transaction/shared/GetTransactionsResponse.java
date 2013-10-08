package tracker.service.transaction.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings( "serial" )
public class GetTransactionsResponse implements Serializable {

	private long id;

	private String description;
	
	private Date creationDate;
		
	private String createdBy;
	
	private List<GetTransactionsResponse.Item> itemList = new LinkedList<GetTransactionsResponse.Item>();
	
	
	public long getId() { return this.id; }
	
	public String getDescription() { return this.description; }
	
	public Date getCreationDate() { return this.creationDate; }

	public String getCreatedBy() { return this.createdBy; }
	
	public List<GetTransactionsResponse.Item> getItemList() { return this.itemList; }


	public void setId( long id ) { this.id = id; }
	
	public void setDescription( String description ) { this.description = description; }
	
	public void setCreationDate( Date creationDate ) { this.creationDate = creationDate; }
	
	public void setCreatedBy( String createdBy ) { this.createdBy = createdBy; }
	
	public void addItem( GetTransactionsResponse.Item item ) { this.itemList.add(item); }
	
	
	public class Item {

		private Long id;

		private String description;
		
		private Date creationDate;
		
		private String createdBy;
			
		
		public long getId() { return this.id; }
		
		public String getDescription() { return this.description; }
		
		public Date getCreationDate() { return this.creationDate; }

		public String getCreatedBy() { return this.createdBy; }

		
		public void setId( long id ) { this.id = id; }
		
		public void setDescription( String description ) { this.description = description; }
		
		public void setCreationDate( Date creationDate ) { this.creationDate = creationDate; }
		
		public void setCreatedBy( String createdBy ) { this.createdBy = createdBy; }
		
	}
	
}
