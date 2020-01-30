package algorithm_2019;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

public class Pa_3_1 {
	static int[] randData1000, randData10000, randData100000;
	static int[] revData1000, revData10000, revData100000;
	static double[][] calcTimes = new double[6][9];

	public static void main(String[] args) {
		initialize();

		calcSortTime();

		printDataTable();
	}

	private static void initialize() {
		initArray();

		initRevData();
	}

	private static void initArray() {
		randData1000 = new int[1000];
		randData10000 = new int[10000];
		randData100000 = new int[100000];

		revData1000 = new int[1000];
		revData10000 = new int[10000];
		revData100000 = new int[100000];
	}

	private static void initRandData() {
		Random r = new Random();

		for (int i = 0; i < randData1000.length; i++) {
			randData1000[i] = r.nextInt(1000) +1;
		}
		for (int i = 0; i < randData10000.length; i++) {
			randData10000[i] = r.nextInt(10000)+1;
		}
		for (int i = 0; i < randData100000.length; i++) {
			randData100000[i] = r.nextInt(100000) + 1;
		}
	}

	private static void initRevData() {
		for (int i = 0; i < revData1000.length; i++) {
			revData1000[i] = 1000 - i;
		}
		for (int i = 0; i < revData10000.length; i++) {
			revData10000[i] = 10000-i;
		}
		for (int i = 0; i < revData100000.length; i++) {
			revData100000[i] = 100000-i;
		}
	}

	private static void calcSortTime() {
		calcSortTimeForRand();
		calcSortTimeForRev();
	}

	private static void calcSortTimeForRand() {
		for (int i = 0; i < 10; i++) {
			initRandData();

			calcAllSortFor(randData1000, calcTimes[0]);
			calcAllSortFor(randData10000, calcTimes[1]);
			calcAllSortFor(randData100000, calcTimes[2]);
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				calcTimes[j][i] /= 10;
			}
		}
	}

	private static void calcSortTimeForRev() {
		calcAllSortFor(revData1000, calcTimes[3]);
		calcAllSortFor(revData10000, calcTimes[4]);
		calcAllSortFor(revData100000, calcTimes[5]);
	}

	private static void calcAllSortFor(int[] data, double[] time) {
		time[0] += bubbleSort(Arrays.copyOf(data, data.length));
		time[1] += selectionSort(Arrays.copyOf(data, data.length));
		time[2] += insertionSort(Arrays.copyOf(data, data.length));
		time[3] += mergeSort(Arrays.copyOf(data, data.length), 0, data.length-1);
		num = 0;
		time[4] += quickSort(Arrays.copyOf(data, data.length), 0, data.length-1, 1); // pivot: last element
		num = 0;
		time[5] += quickSort(Arrays.copyOf(data, data.length), 0, data.length-1, 2); // pivot: medium element
		num = 0;
		time[6] += quickSort(Arrays.copyOf(data, data.length), 0, data.length-1, 3); // pivot: random element
		time[7] += heapSort(Arrays.copyOf(data, data.length));
		time[8] += librarySort(Arrays.copyOf(data, data.length));
	}

	private static long bubbleSort(int[] data) {
		int tmp;

		long start = System.currentTimeMillis();
		for (int i = 0; i < data.length-1; i++) {
			for (int j = 0; j < data.length-i-1; j++) {
				if(data[j] > data[j+1]) {
					tmp = data[j];
					data[j] = data[j+1];
					data[j+1] = tmp;
				}
			}
		}
		long end = System.currentTimeMillis();

		return end-start;
	}

	private static long selectionSort(int[] data) {
		int tmp;
		int maxIndex = 0;

		long start = System.currentTimeMillis();
		for (int i = 0; i < data.length-1; i++) {
			for (int j = 0; j < data.length-i; j++) {
				if(data[j] >= data[maxIndex]) {
					maxIndex = j;
				}
			}
			tmp = data[maxIndex];
			data[maxIndex] = data[data.length-1-i];
			data[data.length-1-i] = tmp;

			maxIndex = 0;
		}
		long end = System.currentTimeMillis();

		return end-start;
	}

	private static long insertionSort(int[] data) {
		int tmp;

		long start = System.currentTimeMillis();
		for (int i = 0; i < data.length-1; i++) {
			for (int j = i+1; j > 0; j--) {
				if(data[j] < data[j-1]) {
					tmp = data[j];
					data[j] = data[j-1];
					data[j-1] = tmp;
				}
			}
		}
		long end = System.currentTimeMillis();

		return end-start;
	}

	private static long mergeSort(int[] data, int p, int r) {
		int q;

		long start = System.currentTimeMillis();
		if(p < r) {
			q = (p+r) /2;
			mergeSort(data, p, q);
			mergeSort(data, q+1, r);
			merge(data, p, q , r);
		}
		long end = System.currentTimeMillis();

		return end - start;
	}

	static int[] tmp = new int[100000];
	private static void merge(int[] data, int p, int q, int r) {
		int i = p, j = q+1, k = p;
		while(i<=q && j<=r) {
			if(data[i] <= data[j])
				tmp[k++] = data[i++];
			else
				tmp[k++] = data[j++];
		}
		while(i<=q)
			tmp[k++] = data[i++];
		while(j<=r)
			tmp[k++] = data[j++];

		if (r + 1 - p >= 0) {
			System.arraycopy(tmp, p, data, p, r + 1 - p);
		}
	}

	static int num = 0;
	private static long quickSort(int[] data, int p, int r, int c) {
		int q;
		if(num > 2000)
			return -1;

		num++;
		long start = System.currentTimeMillis();
		if(p<r) {
			q = partition(data, p, r ,c);
			quickSort(data, p, q-1, c);
			quickSort(data, q+1, r, c);
		}
		long end = System.currentTimeMillis();
		if(num > 2000)
			return -1000;

		num--;
		return end-start;
	}

	private static int partition(int[] data, int p, int r, int c) {
		int i = p-1, tmp;
		int pivot = choosePivot(data, p, r, c);
		tmp = data[pivot];
		data[pivot] = data[r];
		data[r] = tmp;

		for (int j = p; j < r; j++) {
			if(data[j] <= data[r]) {
				i++;
				tmp = data[i];
				data[i] = data[j];
				data[j] = tmp;
			}
		}
		tmp = data[i+1];
		data[i+1] = data[r];
		data[r] = tmp;

		return i+1;
	}

	private static int choosePivot(int[] data, int p, int r, int c) {
		if (c == 1) {
			return r;
		} else if (c == 2) {
			int q = (p+r)/2;
			int min, max;
			if(data[p] >= data[p]) {
				max = p;
				min = q;
			} else {
				max = q;
				min = p;
			}
			if(data[r] >= data[max])
				return max;
			else if (data[r] >= data[min])
				return r;
			else
				return min;
		} else if (c == 3){
			return (int)(Math.random() * (r-p))+p;
		}

		return -1;
	}

	private static double heapSort(int[] data) {
		long start=System.currentTimeMillis();
		buildMaxHeap(data);
		int heapSize = data.length -1;
		for (int i = heapSize; i >= 0; i--) {
			swap(data, 0, i);
			maxHeapify(data, 0, heapSize);
			heapSize--;
		}
		long end=System.currentTimeMillis();
		return end-start;
	}

	private static void maxHeapify(int[] data, int i, int heapSize) {
		if(i*2+1 >= heapSize)
			return;
		int k = i*2+1;
		if((i * 2 + 2 < heapSize) && data[k] < data[i * 2 + 2])
			k = i * 2 + 2;
		if(data[i] > data[k])
			return;
		swap(data,i,k);
		maxHeapify(data,k,heapSize);
	}

	private static void swap(int[] data, int i, int j) {
		int tmp = data[i];
		data[i] = data[j];
		data[j] = tmp;
	}

	private static void buildMaxHeap(int[] data) {
		for(int i = (data.length-2)/2; i >= 0; i--) {
			maxHeapify(data, i, data.length);
		}
	}

	private static double librarySort(int[] data) {
		long start = System.currentTimeMillis();
		Arrays.sort(data);
		long end = System.currentTimeMillis();

		return end - start;
	}

	private static void printDataTable() {
		DecimalFormat df = new DecimalFormat("00.000000");
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 6; j++) {
				String t = df.format(calcTimes[j][i]/1000);
				System.out.print(t + " ");
			}
			System.out.println();
		}
	}

}
