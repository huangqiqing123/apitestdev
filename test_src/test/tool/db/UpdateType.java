package test.tool.db;

/*
 * 执行非查询类sql时，可以指定更新类型，有时会根据更新类型执行必要的处理。
 */
public enum UpdateType {
	
	NONE,
	INSERT,	
	ALTER,
	UPDATE,
	DROP,
	DELETE,
	CREATE;	
}
