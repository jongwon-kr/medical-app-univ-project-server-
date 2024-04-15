
public class test1 {

	public static void main(String[] args) {
		int [] arr = new int[25];
		
		for(int i = 1;i<=arr.length;i++) {
			arr[i-1] = i;
		}
		
		for(int i =0;i<arr.length-2;i++) {
			System.out.print(arr[i]+",");
			System.out.print(arr[i+1]+",");
			System.out.print(arr[i+2]);
			
			System.out.println(" ");
			System.out.println(" ");
		}

	}

}
