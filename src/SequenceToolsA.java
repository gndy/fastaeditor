import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Set;
import org.biojava.bio.Annotation;
import org.biojava.bio.seq.Feature;
import org.biojavax.RankedCrossRef;
import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.bio.seq.RichSequenceIterator;



public class SequenceToolsA {
	static File filein = null;
	static File fileout = null;
	static String gb = null;
	static String gi = null; 
	static String gn,lo, tx, organism, product, translation,DNA, combination;
	static int or = 0;	static int st;
	public static void gb2fast(File filein,File fileout,Boolean dna) {
	
	try {
		BufferedReader br = new BufferedReader(new FileReader(filein));
		RichSequenceIterator rsi = RichSequence.IOTools.readGenbankDNA(br,null);
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileout));

		while (rsi.hasNext()) {
			RichSequence rs = rsi.nextRichSequence();
				gn= GetInfo.get_Gn(rs);
			Set<Feature> rfSet = rs.getFeatureSet();
			for (Iterator iterator = rfSet.iterator(); iterator.hasNext();) {
				RichFeature feature = (RichFeature) iterator.next();
				if (feature.getType().equals("CDS")) {
					Set<RankedCrossRef> rcr = feature.getRankedCrossRefs();
					for (Iterator iterator2 = rcr.iterator(); iterator2.hasNext();) {
						RankedCrossRef rankedCrossRef = (RankedCrossRef) iterator2.next();
						if ((rankedCrossRef.getCrossRef().getDbname()).equals("GI")) {
							gi = rankedCrossRef.getCrossRef().getAccession();
							
					}
					}
				Annotation annotation = feature.getAnnotation();
				
				
				gb = GetInfo.get_Gb(annotation);
				or = GetInfo.get_Or(or);
				st = GetInfo.get_St(feature);
				lo = GetInfo.get_Lo(feature);
				tx = GetInfo.get_Taxid(rs);
				product = GetInfo.get_Product(annotation);
				organism = GetInfo.get_Organism(rs);
				translation = GetInfo.get_Translation(annotation);
				DNA = GetInfo.get_DNA(feature, rs);
				if(dna==true){
					combination = GetInfo.get_Combination()+GetInfo.subStr(DNA);
				}else{
					combination = GetInfo.get_Combination()+GetInfo.subStr(translation);
				}
				writer.write(combination);
			}
			}
			or = 0;
		}
		writer.close();
	}catch (Exception be) {
		be.printStackTrace();
		System.exit(-1);
	}
	}
	
	
		public static void gb2richfan(File filein,File fileout){
			SequenceToolsA.filein = filein;
			SequenceToolsA.fileout = fileout;
			gb2fast(filein, fileout, true);
		}
		public static void gb2richfaa(File filein,File fileout){
			SequenceToolsA.filein = filein;
			SequenceToolsA.fileout = fileout;
			gb2fast(filein, fileout, false);
		}
}