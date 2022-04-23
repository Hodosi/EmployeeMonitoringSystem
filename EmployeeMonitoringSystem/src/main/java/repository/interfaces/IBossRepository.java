package repository.interfaces;

import model.Boss;

public interface IBossRepository extends IRepository<Integer, Boss> {
    Boss findByUsername(String username);
}
