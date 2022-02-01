import {Genre} from "./genre";
import {Author} from "./author";
import {Comment} from "./comment";


export class Book {
  constructor(
    public id: number,
    public title: string,
    public authorList: Author[],
    public genreList: Genre[],
    public commentList: Comment[]) {
  }
}
