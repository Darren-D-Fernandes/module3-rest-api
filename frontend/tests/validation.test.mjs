import test from 'node:test';
import assert from 'node:assert/strict';
import { isValidTitle } from '../src/validation.mjs';

//Test case 1: A normal title is accepted 
test('normal title is accepted', ()=>{
    const result = isValidTitle('Start a coding club');
    assert.equal(result,true);
});

//Test case 2:An empty string is rejected
test('empty string is rejected', () => {
  const result = isValidTitle('');
  assert.equal(result, false);
});

// Test Case 3: A spaces-only string is rejected
test('spaces-only string is rejected', () => {
  const result = isValidTitle('    ');
  assert.equal(result, false);
});