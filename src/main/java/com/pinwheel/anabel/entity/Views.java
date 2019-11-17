package com.pinwheel.anabel.entity;


public final class Views {
    public interface WithId {
    }

    public interface WithMeta {
    }

    public interface WithTimestamps {
    }

    public interface WithGeneral extends WithId, WithMeta {
    }

    public interface WithExtended extends WithGeneral, WithTimestamps {
    }

    public interface WithDependencies extends WithId {
    }

    public interface WithAll extends WithExtended, WithDependencies {
    }
}
