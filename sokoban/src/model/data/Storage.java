package model.data;

import java.io.Serializable;

public abstract class Storage implements Serializable
{
	private static final long serialVersionUID = 1L;

	protected String storageKind;

	public String getStorageKind() 
	{
		return storageKind;
	}
}
