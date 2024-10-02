package org.example.Enums;

import org.example.Implementations.NormalAttendent;
import org.example.Implementations.SmartAttendent;
import org.example.Interfaces.Attendent;

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