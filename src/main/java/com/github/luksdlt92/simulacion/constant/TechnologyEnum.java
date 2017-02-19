package com.github.luksdlt92.simulacion.constant;

public enum TechnologyEnum {
	ANDROID(0),
	IOS(1),
	WEB(2);
	
	private Integer id;
	TechnologyEnum(Integer id){
		this.id=id;
	}
	
	public Integer getId(){
		return this.id;
	}
	
	public String getName(){
		return this.toString().toLowerCase();
	}
	
	public static TechnologyEnum valueOf(Integer id){
		TechnologyEnum en = null;
		switch(id){
		case 0:
			en = TechnologyEnum.ANDROID;
			break;
		case 1:
			en = TechnologyEnum.IOS;
			break;
		case 2:
			en = TechnologyEnum.WEB;
			break;
		}
		return en;
	}
	
	public static TechnologyEnum getValue(String nombre){
		TechnologyEnum en = null;
		if(TechnologyEnum.ANDROID.toString().equalsIgnoreCase(nombre))
			en = TechnologyEnum.ANDROID;
		else if(TechnologyEnum.IOS.toString().equalsIgnoreCase(nombre))
			en = TechnologyEnum.IOS;
		else if(TechnologyEnum.WEB.toString().equalsIgnoreCase(nombre))
			en = TechnologyEnum.WEB;
		return en;
	}
}