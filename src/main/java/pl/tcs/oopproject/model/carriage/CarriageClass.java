package pl.tcs.oopproject.model.carriage;

public enum CarriageClass {
    FIRST_CLASS {
        @Override
        public String toString() {
            return "klasa pierwsza\n";
        }
    }, SECOND_CLASS {
        @Override
        public String toString() {
            return "klasa druga\n";
        }
    }


}
