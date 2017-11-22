package fr.unice.master1.sd.tp3;

import java.util.Arrays;

public class tri_bitonic {

	public static void main(String[] args) {
		/*int[] tri = {5,6,3,8,9,2,1,10};
		bitonicSort(tri);
		
		String t = "[";
		for(int i=0;i<tri.length;i++) {
			if(i<tri.length-1) {
				t+=tri[i]+",";
			}else {
				t+=tri[i];
			}
		}
		t+="]";
		System.out.println(t);*/
		
		int[] tri = {5,6,3,8,9,2,1,10};
		bitonicSort(tri,2);
		
		String t = "[";
		for(int i=0;i<tri.length;i++) {
			if(i<tri.length-1) {
				t+=tri[i]+",";
			}else {
				t+=tri[i];
			}
		}
		t+="]";
		System.out.println(t);
		
	}
	
	public static void bitonicSort(int[] a) {
        if (a.length >= 2) {
                // découpage
                int[] left  = Arrays.copyOfRange(a, 0, a.length / 2);
                int[] right = Arrays.copyOfRange(a, a.length / 2, a.length);

                // tri des deux parties
                bitonicSort(left);
                bitonicSort(right);
                // fusion des parties
                fusionner(left, right, a);
        }
	}
	
	public static void bitonicSort(int[] a, int nCore) {
		if(nCore>1) {
			int[] left  = Arrays.copyOfRange(a, 0, a.length / 2);
            int[] right = Arrays.copyOfRange(a, a.length / 2, a.length);
			Thread t1 = new Thread(new Runnable() {

				@Override
				public void run() {
					bitonicSort(left,nCore/2);
				}
				
			});
			Thread t2 = new Thread(new Runnable() {

				@Override
				public void run() {
					bitonicSort(right,nCore/2);
				}
				
			});
			t1.start();
			t2.start();
			try {
				t1.join();
				t2.join();
				fusionner(left,right,a);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}else {
			bitonicSort(a);
		}
	}

	private static void fusionner(int[] left, int[] right, int[] a) {
		int dim1 = left.length ;
		int dim2 = right.length ;

		int a1 = 0 ;
		int b = 0 ;
		int c = 0 ;
		
		while ( c < dim1+dim2-1 )
		{
			if ( left[a1] < right[b] ){
				a[c] = left[a1] ;
				a1++ ;
				c++ ;
				if ( a1 > dim1-1 ){
					while ( b < dim2){
						a[c] = right[b] ;
						b++;
						c++;
					}
				}
			}else if ( left[a1] >= right[b] ){
					a[c] = right[b] ;
					b++ ;
					c++ ;
					if ( b > dim2-1 ){
						while ( a1 < dim1){
							a[c] = left[a1] ;
							a1 ++ ;
							c++;
						}
					}
				}
		}
	}

}
