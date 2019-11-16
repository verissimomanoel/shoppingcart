import { Pipe, PipeTransform } from '@angular/core';

/**
 *  Alpha pipe.
 * 
 * @author Manoel Veríssimo
 */
@Pipe({
  name: 'alpha'
})
export class AlphaPipe implements PipeTransform {

  private alphabet: Array<string> = [
    'a', 'b', 'c', 'd',
    'e', 'f', 'g', 'h',
    'i', 'j', 'k', 'l',
    'm', 'n', 'o', 'p',
    'q', 'r', 's', 't',
    'u', 'v', 'w', 'x',
    'y', 'z'
  ];

  /**
   * @param index 
   * @param args 
   */
  transform(index: number, ...args: string[]): any {
    let letter: string = this.alphabet[index - 1];

    if (letter && args.indexOf('uppercase') !== -1) {
      letter = letter.toUpperCase();
    }
    return letter;
  }
}
