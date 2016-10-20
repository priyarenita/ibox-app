package edu.csupomona.cs585.ibox;


import org.junit.*;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Files.List;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import java.util.Arrays;

import edu.csupomona.cs585.ibox.sync.GoogleDriveFileSyncManager;


public class GoogleDriveFileSyncManagerTest {

	private GoogleDriveFileSyncManager fileSyncManager; 
	private Drive drive; 
	private Drive service; 
	private Files mockFiles;
	private Files.Insert mockInsert;
	private Files.Update mockUpdate; 
	private Files.Delete mockDelete; 
	//private Files.List mockList; 
	//private FileList mockFileList; 
	

	
	@Before
	public void setUp(){
		drive = mock(Drive.class); 
		fileSyncManager = new GoogleDriveFileSyncManager(drive);
		mockFiles = mock(Files.class); 
		mockInsert = mock(Files.Insert.class); 
		mockUpdate = mock(Files.Update.class); 
		mockDelete = mock(Files.Delete.class); 
		//mockList = mock(Files.List.class); 
		//mockFileList = mock(FileList.class); 
		
	
	}

	
	@Test
	public void testAddFile(){
		java.io.File file = new java.io.File("test"); 
		
		try {
			//Stub mock objects 
			when(drive.files()).thenReturn(mockFiles);
			when(drive.files().insert(any(File.class), any(FileContent.class))).thenReturn(mockInsert); 
			when(drive.files().insert(any(File.class), any(FileContent.class)).execute()).thenReturn(new File());
			
			//test addFile method
			fileSyncManager.addFile(file);
			
			//verify method calls
			verify(drive, times(3)).files();
			verify(mockFiles,times(2)).insert(any(File.class), any(FileContent.class)); 
			verify(mockInsert,times(1)).execute(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test(expected=IOException.class)
	public void testAddFileWithExecption() throws IOException{
		java.io.File file = new java.io.File("test"); 
		
			//Stub mock objects 
			when(drive.files()).thenReturn(mockFiles);
			when(drive.files().insert(any(File.class), any(FileContent.class))).thenReturn(mockInsert); 
			when(drive.files().insert(any(File.class), any(FileContent.class)).execute()).thenThrow(new IOException());
			
			//test addFile method
			fileSyncManager.addFile(file);
			
			//verify method calls
			verify(drive, times(3)).files();
			verify(mockFiles,times(2)).insert(any(File.class), any(FileContent.class)); 
			verify(mockInsert,times(1)).execute(); 

	}
	
	@Test
	public void testUpdateFileWithNullFileId(){
		java.io.File file = new java.io.File("test"); 
		List mockList = mock(List.class);
		
		File f = new File(); 
		f.setTitle("test");
		java.util.List<File> fList = Arrays.asList(f); 
		
		
 		FileList fileList = new FileList(); 
		fileList.setItems(fList); 
		
		
		try {
			//Stub mock objects 
			when(drive.files()).thenReturn(mockFiles);
			when(drive.files().list()).thenReturn(mockList); 
			when(mockList.execute()).thenReturn(fileList);
		
			when(drive.files().insert(any(File.class), any(FileContent.class))).thenReturn(mockInsert); 
			when(drive.files().insert(any(File.class), any(FileContent.class)).execute()).thenReturn(new File());
			
			//test addFile method
			fileSyncManager.updateFile(file);
			
			//verify method calls
			verify(drive, times(5)).files();
			verify(mockFiles,times(2)).insert(any(File.class), any(FileContent.class)); 
			verify(mockInsert,times(1)).execute(); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testUpdateFileWithOutNullFileId(){
		java.io.File file = new java.io.File("test"); 
		List mockList = mock(List.class);
		
		File f = new File(); 
		f.setTitle("test");
		f.setId("123"); 
		java.util.List<File> fList = Arrays.asList(f); 
		
		
 		FileList fileList = new FileList(); 
		fileList.setItems(fList); 
	
		try {
			//Stub mock objects 
			when(drive.files()).thenReturn(mockFiles);
			when(drive.files().list()).thenReturn(mockList); 
			when(mockList.execute()).thenReturn(fileList);
			
			when(drive.files().update(any(String.class), any(File.class), any(FileContent.class))).thenReturn(mockUpdate); 
			when(drive.files().update(any(String.class), any(File.class), any(FileContent.class)).execute()).thenReturn(f); 
		
			//test updateFile method
			fileSyncManager.updateFile(file);
		
			//verify method calls
			verify(drive, times(5)).files();
			verify(mockFiles,times(2)).update(any(String.class), any(File.class), any(FileContent.class)); 
			verify(mockUpdate,times(1)).execute(); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test(expected = IOException.class)
	public void testUpdateFileWithException() throws IOException{
		java.io.File file = new java.io.File("test"); 
		List mockList = mock(List.class);
		
		File f = new File(); 
		f.setTitle("test");
		f.setId("123"); 
		java.util.List<File> fList = Arrays.asList(f); 
		
		
 		FileList fileList = new FileList(); 
		fileList.setItems(fList); 

			when(drive.files()).thenReturn(mockFiles);
			when(drive.files().list()).thenReturn(mockList); 
			when(mockList.execute()).thenReturn(fileList);
			
			when(drive.files().update(any(String.class), any(File.class), any(FileContent.class))).thenReturn(mockUpdate); 
			when(drive.files().update(any(String.class), any(File.class), any(FileContent.class)).execute()).thenThrow(new IOException()); 
		
			//test updateFile method
			fileSyncManager.updateFile(file);
			
			
			//verify method calls
			verify(drive, times(5)).files();
			verify(mockFiles,times(2)).update(any(String.class), any(File.class), any(FileContent.class)); 
			verify(mockUpdate,times(1)).execute(); 
			
	}

	@Test
	public void testDeleteFile(){
		java.io.File file = new java.io.File("test"); 
		List mockList = mock(List.class);
		
		File f = new File(); 
		f.setTitle("test");
		f.setId("123"); 
		java.util.List<File> fList = Arrays.asList(f); 
		
		
 		FileList fileList = new FileList(); 
		fileList.setItems(fList); 
	
		try {
			//Stub mock objects 
			when(drive.files()).thenReturn(mockFiles);
			when(drive.files().list()).thenReturn(mockList); 
			when(mockList.execute()).thenReturn(fileList);
			
			when(drive.files().delete(any(String.class))).thenReturn(mockDelete); 
			when(drive.files().delete(any(String.class)).execute()).thenReturn(null);
			// doNothing().when(mockDelete).execute();
		
			//test updateFile method
			fileSyncManager.deleteFile(file);
		
			//verify method calls
			verify(drive, times(5)).files();
			verify(mockFiles,times(2)).delete(any(String.class)); 
			verify(mockDelete,times(1)).execute(); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test(expected = IOException.class)
	public void testDeleteFileWithException() throws IOException{
		java.io.File file = new java.io.File("test"); 
		List mockList = mock(List.class);
		
		File f = new File(); 
		f.setTitle("test");
		f.setId("123"); 
		java.util.List<File> fList = Arrays.asList(f); 
		
		
 		FileList fileList = new FileList(); 
		fileList.setItems(fList); 
	
			//Stub mock objects 
			when(drive.files()).thenReturn(mockFiles);
			when(drive.files().list()).thenReturn(mockList); 
			when(mockList.execute()).thenReturn(fileList);
			
			when(drive.files().delete(any(String.class))).thenReturn(mockDelete); 
			when(drive.files().delete(any(String.class)).execute()).thenThrow(new IOException());
			// doNothing().when(mockDelete).execute();
		
			//test updateFile method
			fileSyncManager.deleteFile(file);
		
			//verify method calls
			verify(drive, times(5)).files();
			verify(mockFiles,times(2)).delete(any(String.class)); 
			verify(mockDelete,times(1)).execute(); 

	}
	
	@Test(expected = java.io.FileNotFoundException.class)
	public void testDeleteFileWithNullFileIdWithException() throws IOException{
		java.io.File file = new java.io.File("test"); 
		List mockList = mock(List.class);
		
		File f = new File(); 
		f.setTitle("test");
		java.util.List<File> fList = Arrays.asList(f); 
		
		
 		FileList fileList = new FileList(); 
		fileList.setItems(fList); 
		
		
			when(drive.files()).thenReturn(mockFiles);
			when(drive.files().list()).thenReturn(mockList); 
			when(mockList.execute()).thenReturn(fileList);
		
			when(drive.files().insert(any(File.class), any(FileContent.class))).thenReturn(mockInsert); 
			when(drive.files().insert(any(File.class), any(FileContent.class)).execute()).thenReturn(new File());
			
			//test addFile method
			fileSyncManager.deleteFile(file);
			 
	}

	@Test
	public void testGetFileId(){
		java.io.File file = new java.io.File("test"); 
		List mockList = mock(List.class);
		
		File f = new File(); 
		f.setTitle("test");
		f.setId("123"); 
		java.util.List<File> fList = Arrays.asList(f); 
		
		
 		FileList fileList = new FileList(); 
		fileList.setItems(fList); 
	
		try {
			//Stub mock objects 
			when(drive.files()).thenReturn(mockFiles);
			when(drive.files().list()).thenReturn(mockList); 
			when(mockList.execute()).thenReturn(fileList);

		
			//test updateFile method
			String id = fileSyncManager.getFileId(file.getName());
			assertEquals("123", id); 

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testGetFileIdWithException() throws IOException{
		java.io.File file = new java.io.File("test"); 
		List mockList = mock(List.class);
		
		File f = new File(); 
		f.setTitle("test");
		f.setId("123"); 
		java.util.List<File> fList = Arrays.asList(f); 
		
		
 		FileList fileList = new FileList(); 
		fileList.setItems(fList); 

			//Stub mock objects 
			when(drive.files()).thenReturn(mockFiles);
			when(drive.files().list()).thenReturn(mockList); 
			when(mockList.execute()).thenThrow(new IOException());

			//test updateFile method
			String id = fileSyncManager.getFileId(file.getName());
			//assertEquals("123", id); 

	}

	

}












