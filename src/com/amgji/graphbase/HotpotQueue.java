package com.amgji.graphbase;

public class HotpotQueue {

	private final int fir;
	private final int sec;
	private final int thi;
	private final int fou;
	private final int fif;
	private final int six;

	public HotpotQueue(int fir, int sec, int thi, int fou, int fif, int six) {
		super();
		this.fir = fir;
		this.sec = sec;
		this.thi = thi;
		this.fou = fou;
		this.fif = fif;
		this.six = six;
	}
	

	public int getFir() {
		return fir;
	}


	public int getSec() {
		return sec;
	}


	public int getThi() {
		return thi;
	}


	public int getFou() {
		return fou;
	}


	public int getFif() {
		return fif;
	}


	public int getSix() {
		return six;
	}


	@Override
	public String toString() {
		return "HotpotQueue [fir=" + fir + ", sec=" + sec + ", thi=" + thi + ", fou=" + fou + ", fif=" + fif + ", six="
				+ six + "]";
	}
}
