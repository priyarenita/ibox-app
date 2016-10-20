package edu.csupomona.cs585.ibox;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.TemporaryFolder;

import com.google.api.services.drive.model.File;

import edu.csupomona.cs585.ibox.sync.FileSyncManager;
import edu.csupomona.cs585.ibox.sync.GoogleDriveFileSyncManager;
import edu.csupomona.cs585.ibox.sync.GoogleDriveServiceProvider;

public class GoogleDriveFileSyncManagerIntegrationTest {

	private FileSyncManager fileSyncManagerTest;


	//@Rule
	public TemporaryFolder folder = new TemporaryFolder(); 
	
	
	//@Rule 
	public ExternalResource resource = new ExternalResource(){
		protected void before(){
			fileSyncManagerTest = new GoogleDriveFileSyncManager(
			 		GoogleDriveServiceProvider.get().getGoogleDriveClient());
		};
	};

	//@Test
	public void testAddFileIntegration(){
		 try {	
			java.io.File newFile = folder.newFile("testAddFile.txt");
			
			fileSyncManagerTest.addFile(newFile);
			java.util.List<File> allFiles = ((GoogleDriveFileSyncManager) fileSyncManagerTest).retrieveAllFiles(); 
			
			boolean check = false;
			for(File f: allFiles){
				if(newFile.getName().equals(f.getTitle()))
					System.out.println(f.getTitle());
					check = true; 
			}
			
			assertTrue(check); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
	}
	
	//@Test
	public void testUpdateFileIntegration(){
	
		 try {	
			
			java.io.File file = folder.newFile("testUpdateFile.txt");
			FileWriter writer = new FileWriter(file); 
			writer.write("text");
			//writer.flush();
			writer.close();
			
			fileSyncManagerTest.addFile(file);
			
			
			fileSyncManagerTest.updateFile(file);
			java.util.List<File> allFiles = ((GoogleDriveFileSyncManager) fileSyncManagerTest).retrieveAllFiles(); 
			
			boolean check = false;
			for(File f: allFiles){
				if(file.getName().equals(f.getTitle())){				
					check = true; 
				}
			}
			
			assertTrue(check); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
	}
	
	//@Test
	public void testDeleteFileIntegration(){

	
		 try {	
			java.io.File file = folder.newFile("testDeleteFile.txt");
			
			fileSyncManagerTest.addFile(file);
			fileSyncManagerTest.deleteFile(file);
			java.util.List<File> allFiles = ((GoogleDriveFileSyncManager) fileSyncManagerTest).retrieveAllFiles(); 
			
			boolean check = false;
			for(File f: allFiles){
				if(file.getName().equals(f.getTitle()))
					check = true; 
			}
			
			assertFalse(check); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
	}
}
