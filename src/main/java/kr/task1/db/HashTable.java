package kr.task1.db;

import kr.task1.db.entities.ProviderEntity;

public class HashTable<T extends ProviderEntity> {
    private static final int DEFAULT_CAPACITY = 101;
    private final ProviderEntity[] table;
    private final int capacity;   //max number of rows
    private int size;       //current number of rows
    private int idCounter;

    //capacity must be a prime number
    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    public HashTable(int capacity) {
        if (capacity <= 0) {
            capacity = 101;
        }
        this.table = new ProviderEntity[capacity];
        this.capacity = capacity;
        size = 0;
        idCounter = 0;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }

    public int getIdCounter() {
        return idCounter;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean ifIdExist(int id) {
        if(id<0) return false;
        return getHashByKey(id)!=-1;
    }

    public ProviderEntity[] getTable() {
        return table;
    }

    public void insert(T entity) throws Exception {
        int hash = getEmptyHash(idCounter);
        if (hash == -1) {
            throw new Exception("The hashtable is fully filled!");
        }

        table[hash] = entity;
        size++;
        idCounter++;
    }

    public void remove(int id) throws Exception {
        int hash = getHashByKey(id);

        if (isEmpty() || hash == -1) {
            throw new Exception("This id is not exist, impossible to remove: " + id);
        }

        table[hash] = null;
        size--;
    }

    public ProviderEntity search(int id) {
        int hash = getHashByKey(id);

        if (isEmpty() || hash == -1) {
            return null;
        }

        return table[hash];
    }

    //if returns -1 is filled
    private int getEmptyHash(int id) {
        if (isFull()) {
            return -1;
        }
        int hash = hash1(id);
        int hash2 = hash2(id);

        int i = hash;
        do {
            i = (i + hash2) % capacity;
        } while (table[i] != null);
        return i;
    }

    //if returns -1 not exist
    private int getHashByKey(int id) {
        if(id<0) {
            return -1;
        }

        int hash = hash1(id);
        int hash2 = hash2(id);
        boolean hashReached = false;

        int i = hash;
        do {
            if (table[i] != null && table[i].getId() == id) {
                return i;
            }
            //existing check
            if (i == hash) {
                if (!hashReached) {
                    hashReached = true;
                } else {
                    return -1;
                }
            }
            i = (i + hash2) % capacity;
        } while (true);
    }

    private int hash1(int key) {
        return key % capacity;
    }

    private int hash2(int key) {
        return key % (int) Math.sqrt(capacity) + 1;
    }
}