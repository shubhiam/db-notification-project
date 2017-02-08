package hello;

import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.TableChangeDescription;

class DCNListener implements DatabaseChangeListener {
	  DBChangeNotify dcn;
	  DCNListener(DBChangeNotify dem) {
	    dcn = dem;
	  }

	public void onDatabaseChangeNotification(DatabaseChangeEvent e) {
		TableChangeDescription[] tc = e.getTableChangeDescription();
		List<String> dbChangeList = new ArrayList<String>();

		for (int i = 0; i < tc.length; i++) {
			RowChangeDescription[] rcds = tc[i].getRowChangeDescription();
			for (int j = 0; j < rcds.length; j++) {
				System.out.println(rcds[j].getRowOperation() + " " + rcds[j].getRowid().stringValue());
				dbChangeList.add(rcds[j].getRowOperation() + " " + rcds[j].getRowid().stringValue());
			}
		}
		GreetingController.dBChanges.addAll(dbChangeList);
		synchronized (dcn) {
			dcn.notify();
		}
	}
	  
	}