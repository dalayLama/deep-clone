package org.example.clone;

import org.example.clone.impl.CloneResolverManager;
import org.example.clone.impl.DefaultObjectMetaInfoReader;
import org.example.clone.impl.DefaultObjectTypeDeterminer;
import org.example.clone.impl.makers.*;

import java.util.List;

/**
 * Just for convenience of creating
 * Spring.....I miss you... :(
 */
public final class CloneLibInitializer {

    private CloneLibInitializer() {}

    public static CloneLib createCloneLib() {
        DefaultObjectTypeDeterminer determiner = new DefaultObjectTypeDeterminer();
        DefaultObjectMetaInfoReader infoReader = new DefaultObjectMetaInfoReader(determiner);

        CloneResolverManager manager = new CloneResolverManager();
        NullCloneMaker nullCloneMaker = new NullCloneMaker();
        SimpleCloneMaker simpleCloneMaker = new SimpleCloneMaker();
        ComplexCloneMaker complexCloneMaker = new ComplexCloneMaker(manager);
        ArrayCloneMaker arrayCloneMaker = new ArrayCloneMaker(infoReader, manager);
        CollectionCloneMaker collectionCloneMaker = new CollectionCloneMaker(infoReader, manager);
        MapCloneMaker mapCloneMaker = new MapCloneMaker(infoReader, manager);

        manager.addResolvers(List.of(
                nullCloneMaker, complexCloneMaker, simpleCloneMaker,
                arrayCloneMaker, collectionCloneMaker, mapCloneMaker
        ));

        return new CloneLib(infoReader, manager);
    }

}
