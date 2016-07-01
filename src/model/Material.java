package model;

public class Material {
	
	private int idMaterial;
	private String materialName;
	private double vkPreis;
	
	
	public Material(int idMaterial, String materialName, double vkPreis) {
		super();
		this.idMaterial = idMaterial;
		this.materialName = materialName;
		this.vkPreis = vkPreis;
	}
	

	public int getIdMaterial() {
		return idMaterial;
	}

	public String getMaterialName() {
		return materialName;
	}

	public double getVkPreis() {
		return vkPreis;
	}

	@Override
	public String toString() {
		return materialName+" à EUR " + vkPreis ;
	}
	
	
	
}
