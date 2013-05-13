import java.util.Iterator;
import org.biojava.bio.Annotation;
import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.RichSequence;


public class GetInfo {
	public  static String subStr(String Str){
		 StringBuilder sb = new StringBuilder("");
		 int width = 80;
			for(int i = 0;i<=Str.length();i+=width){
				int end = Math.min(i + width - 1, Str.length());
				sb =sb.append(Str.substring(i, end)+"\n");
			}
				return sb.toString();
		}
		 
	 public static String get_Gb(Annotation annotation){
		if (annotation.containsProperty("protein_id")) {
			return (String) annotation.getProperty("protein_id");
		} else {
			return "none";
		}
	}
	
	 public static int get_Or(int or){
		or++;
		return or;
	 	}
	 
	 public static int get_St(RichFeature feature){
		 if (feature.getStrand().toString().equals("POSITIVE")) {
				return 1;
			} else {
				return -1;
			}
		}
	 
	 public static String get_Lo(RichFeature feature){
		 String location=null;
		 StringBuilder sb = new StringBuilder();
		 if(feature.getLocation().isContiguous()){
			 return feature.getLocation().toString().replaceAll("\\..", "-");
		 }else
		 {for(Iterator lo = feature.getLocation().blockIterator();lo.hasNext();){
			 location = lo.next().toString();
			 sb = sb.append("(").append(location).append(")").append(",");
			 
		 }
			 
		 return sb.toString().substring(0, sb.toString().length()-1).replaceAll("\\..", "-");
	 }
	 }
	
	 public static String get_Gn(RichSequence rs){
		return rs.getAccession();
		 
	 }
	
	public static String get_Taxid(RichSequence rs){
		return rs.getTaxon().toString().replaceAll("\\[scientific.*\\]","").replaceAll("[a-zA-z]*:", "");
	}
	
	public static String get_Product(Annotation annotation){
		if (annotation.containsProperty("product")) {
			return (String) annotation.getProperty("product");
		} else if (annotation.containsProperty("locus_tag")) {
			return (String) annotation.getProperty("locus_tag");
		} else if (annotation.containsProperty("note")) {
			return (String) annotation.getProperty("note");
		} else {
			return "none";
		}
	}
	
	public static String get_Organism(RichSequence rs){
		return rs.getTaxon().getDisplayName();
	}
	
	public static String get_Translation(Annotation annotation){
		return annotation.getProperty("translation").toString();
	}
	public static String get_DNA(RichFeature feature,RichSequence rs){
		return feature.getLocation().symbols(rs.getInternalSymbolList()).seqString();
	}
	public static String format_DNA(String string){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<string.length();i+=60){
			String sub=null;
			sb.append((i+1)+" ");
			
			if(i+60<=string.length()){
				sub = string.substring(i, i+60);
				for(int j=0;j<60;j+=10){
					sb=sb.append(sub.substring(j, j+10)+" ");
				}
			}else{
				sub=string.substring(i, string.length());
				for(int j=0;j<sub.length();j+=10){
					if(j+10<=sub.length()){
					sb=sb.append(sub.substring(j, j+10)+" ");
					}else{
						sb = sb.append(sub.substring(j, sub.length())+" ");
					}
				}
			}
			sb=sb.append("\n");
		}
		return sb.toString();
	}
	public static String get_Combination(){
		StringBuilder sb = new StringBuilder();
		sb.append(">gi|").append(SequenceToolsA.gi).append("|");
		
		sb.append("gb|").append(SequenceToolsA.gb).append("|");
		
		sb.append("or|").append(SequenceToolsA.or).append("|");
		
		sb.append("st|").append(SequenceToolsA.st).append("|");
		
		sb.append("lo|").append(SequenceToolsA.lo).append("|");
		
		sb.append("gn|").append(SequenceToolsA.gn).append("|");
		
		sb.append("tx|").append(SequenceToolsA.tx).append("|");
		
		sb.append(" "+SequenceToolsA.product+" ");
		
		sb.append("["+SequenceToolsA.organism+"]\n");
		
		return sb.toString();
	}

}

