package HDFSOperations;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
//import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
//import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

//import HDFSOperations.HDFSUse;


//import com.nielsen.dmle.text.common.util.HDFSUtils;

public class CopyFromLfs {


	private static String hdfsUrl;
	private static String hdfsUser;
	public static final int TAIL_SIZE_MATH_VARIABLE = 1024;
	public static final int MATH_TEMP_DATA = 4096;
	
	public CopyFromLfs(final String hdfsurl, final String hdfsuser)
	{
		 initialize(hdfsurl, hdfsuser);
		}
	public CopyFromLfs(){
		//This is default constructor
	}
	private void initialize(final String hdfsurl, final String hdfsuser) {
		hdfsUrl = hdfsurl;
		hdfsUser = hdfsuser;
	}
	
	public Configuration getConfig(final String pathStr) {
		Configuration configuration = new Configuration();
				configuration.set("fs.defaultFS", hdfsUrl);
				configuration.set("hadoop.job.ugi", hdfsUser);
				configuration.set("fs.hdfs.impl",
						org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
				configuration.set("fs.file.impl",
						org.apache.hadoop.fs.LocalFileSystem.class.getName());
				if (pathStr != null) 
				{
					configuration.addResource(new Path(pathStr));
				}
				return configuration;
				}
	
	public static void main(String args[]) throws Exception {
			
		try{
			
		String localSrc = args [0];
		String dst = args[1];
		System.out.println("Hello");
		//ListFilesByPattern listFilesByPattern = new ListFilesByPattern();
		//listFilesByPattern.listFilesByPattern("/DMLE/bfm-Dev/generated/H1QCPredictions/output");
		CopyFromLfs cp = new CopyFromLfs("hdfs://nameservice1", "dmleuser");
			
		URI uri = new URI("hdfs://nameservice1");
		
		FileInputStream file = new FileInputStream(localSrc);
		
		BufferedInputStream in = new BufferedInputStream(file);
		
		FileSystem fs = FileSystem.get(uri, cp.getConfig(dst));
		
	    OutputStream out = fs.create(new Path(dst));
				 
				//Copy file from local to HDFS
				
				IOUtils.copyBytes(in, out, 4096, true);
				 
				System.out.println(dst);
				 
			
	}catch(Throwable t){
		t.printStackTrace();
    }
	}
		}

	
			