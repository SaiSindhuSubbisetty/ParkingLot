package org.example.Enums;

import org.example.Attendent;
import org.example.NormalAttendent;
import org.example.SmartAttendent;

public enum OwnerType {
    SMART {
        @Override
        public Attendent getAttendant() {
            return new SmartAttendent();
        }
    },
    NORMAL {
        @Override
        public Attendent getAttendant() {
            return new NormalAttendent();
        }
    };

    public abstract Attendent getAttendant();
}

