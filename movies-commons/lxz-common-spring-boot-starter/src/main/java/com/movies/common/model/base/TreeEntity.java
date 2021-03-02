package com.movies.common.model.base;

import java.util.List;

/**
 * 树形实体类
 * @author lx Zhang.
 * @date 2019/8/31
 * @param <E>
 */
public interface TreeEntity<E> {

	/**
	 * 树ID
	 * @return
	 */
	 Long getId();

	/**
	 * 树父ID
	 * @return
	 */
	Long getParentId();

	/**
	 * 树 子集合
	 * @param childList
	 */
	 void setChildList(List<E> childList);

}