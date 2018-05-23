export function assertEquals(a, b, msg) {
    let math = a.equals(b);
    assert.equal(math, true, msg);
}

export function assertBigger(a, b, msg) {
    let bigger = a.greaterThan(b);
    assert.equal(bigger, true, msg)
}

export function assertSmaller(a, b, msg) {
    let smaller = a.lessThan(b);
    assert.equal(smaller, true, msg);
}