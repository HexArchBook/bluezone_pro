package io.github.hexarchbook.bluezone.lib.javautils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public final class CollectionUtils {

	private CollectionUtils() { }

	public static boolean isNullOrEmpty ( Collection<?> aCollection ) {
		return ( aCollection==null || aCollection.isEmpty() );
	}

	public static boolean hasAnyNullElement ( Collection<?> aCollection ) {
		if ( isNullOrEmpty(aCollection) ) {
			return false;
		}
		for ( Object element : aCollection ) {
			if ( element==null ) {
				return true;
			}
		}
		return false;
	}

	public static Object[] toArray ( Collection<?> aCollection )  {
		if ( isNullOrEmpty(aCollection) ) {
			return new Object[0];
		}
		return aCollection.stream().toArray(Object[]::new);
	}

	public static boolean areEqualSets (Set<?> aSet, Set<?> anotherSet ) {
        if ( aSet==null && anotherSet==null ) {
            return true;
        }
        if ( aSet==null || anotherSet==null ) {
            return false;
        }
        if ( aSet.size() != anotherSet.size() ) {
            return false;
        }
        return aSet.containsAll ( anotherSet );
    }

	public static <T> Set<T> listToSet ( List<T> aList ) {
		return new HashSet<T>(aList);
	}

}
